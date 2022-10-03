package team7.simple.domain.auth.basic.service;



import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team7.simple.domain.auth.basic.dto.LoginRequestDto;
import team7.simple.domain.auth.basic.dto.SignupRequestDto;
import team7.simple.domain.auth.jwt.dto.TokenRequestDto;
import team7.simple.domain.auth.jwt.dto.TokenResponseDto;
import team7.simple.domain.auth.jwt.repository.RefreshTokenRedisRepository;
import team7.simple.domain.user.entity.User;
import team7.simple.domain.user.repository.UserJpaRepository;
import team7.simple.global.error.advice.exception.*;
import team7.simple.global.security.JwtProvider;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {

    private final UserJpaRepository userJpaRepository;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;

    public Long signup(SignupRequestDto signupRequestDto) {
        if (userJpaRepository.findByAccount(signupRequestDto.getAccount()).isPresent())
            throw new CUserExistException();
        log.info(signupRequestDto.getPassword());
        return userJpaRepository.save(signupRequestDto.toEntity(passwordEncoder)).getUserId();
    }

    public TokenResponseDto login(LoginRequestDto loginRequestDto){
        User user = userJpaRepository.findByAccount(loginRequestDto.getAccount()).orElseThrow(CUserNotFoundException::new);

        if (!passwordEncoder.matches(loginRequestDto.getPassword(), user.getPassword()))
            throw new CWrongPasswordException();

        String accessToken = jwtProvider.generateAccessToken(user.getAccount(), user.getRoles());
        String refreshToken = jwtProvider.generateRefreshToken(user.getAccount(),user.getRoles());

        return TokenResponseDto.builder()
                .grantType("bearer")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
