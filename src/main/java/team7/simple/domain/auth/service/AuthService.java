package team7.simple.domain.auth.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team7.simple.domain.auth.dto.LoginRequestDto;
import team7.simple.domain.auth.dto.SignupRequestDto;
import team7.simple.domain.auth.jwt.dto.TokenResponseDto;
import team7.simple.domain.auth.jwt.entity.LogoutAccessToken;
import team7.simple.domain.auth.jwt.entity.RefreshToken;
import team7.simple.domain.auth.jwt.repository.ActiveAccessTokenRedisRepository;
import team7.simple.domain.auth.jwt.repository.LogoutAccessTokenRedisRepository;
import team7.simple.domain.auth.jwt.repository.RefreshTokenRedisRepository;
import team7.simple.domain.player.service.PlayerService;
import team7.simple.domain.user.entity.User;
import team7.simple.domain.user.error.exception.CUserExistException;
import team7.simple.domain.user.error.exception.CUserNotFoundException;
import team7.simple.domain.user.error.exception.CWrongPasswordException;
import team7.simple.domain.user.repository.UserJpaRepository;
import team7.simple.global.security.JwtProvider;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {

    private final UserJpaRepository userJpaRepository;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenRedisRepository refreshTokenRedisRepository;
    private final LogoutAccessTokenRedisRepository logoutAccessTokenRedisRepository;
    private final ActiveAccessTokenRedisRepository activeAccessTokenRedisRepository;

    private final ConflictService conflictService;
    private final PlayerService playerService;

    public String signup(SignupRequestDto signupRequestDto) {
        if (userJpaRepository.findByAccount(signupRequestDto.getAccount()).isPresent())
            throw new CUserExistException();
        return userJpaRepository.save(signupRequestDto.toEntity(passwordEncoder)).getId();
    }

    public TokenResponseDto login(LoginRequestDto loginRequestDto) {
        User user = userJpaRepository.findByAccount(loginRequestDto.getAccount()).orElseThrow(CUserNotFoundException::new);

        if (!passwordEncoder.matches(loginRequestDto.getPassword(), user.getPassword()))
            throw new CWrongPasswordException();

        String accessToken = jwtProvider.generateAccessToken(user.getAccount(), user.getRoles());
        String refreshToken = jwtProvider.generateRefreshToken(user.getAccount(), user.getRoles());

        refreshTokenRedisRepository.save(new RefreshToken(user.getId(), refreshToken));

        return TokenResponseDto.builder()
                .grantType("bearer")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public void logout(String accessToken, User user) {
        long remainMilliSeconds = jwtProvider.getExpiration(accessToken);

        refreshTokenRedisRepository.deleteById(user.getId());
        logoutAccessTokenRedisRepository.save(new LogoutAccessToken(accessToken, remainMilliSeconds));
        activeAccessTokenRedisRepository.deleteById(accessToken);
    }

    public void withdrawal(String accessToken, User user) {
        logout(accessToken, user);
        userJpaRepository.deleteById(user.getId());
    }
}
