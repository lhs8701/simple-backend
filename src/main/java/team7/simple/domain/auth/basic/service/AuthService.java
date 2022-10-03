package team7.simple.domain.auth.basic.service;



import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team7.simple.domain.auth.basic.dto.LoginRequestDto;
import team7.simple.domain.auth.basic.dto.SignupRequestDto;
import team7.simple.domain.auth.jwt.dto.TokenRequestDto;
import team7.simple.domain.auth.jwt.dto.TokenResponseDto;
import team7.simple.domain.auth.jwt.entity.JwtExpiration;
import team7.simple.domain.auth.jwt.entity.RefreshToken;
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
    private final RefreshTokenRedisRepository refreshTokenRedisRepository;

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

//        String accessToken = jwtProvider.generateAccessToken(user.getUserId(), user.getRoles());
//        String refreshToken = jwtProvider.generateRefreshToken(user.getUserId(), user.getRoles());
        String accessToken = "testAccessToken";
        String refreshToken = "testRefreshToken";
        TokenResponseDto tokenResponseDto = jwtProvider.createTokenDto(accessToken, refreshToken);

//        refreshTokenRedisRepository.save(new RefreshToken(user.getUserId(), tokenResponseDto.getRefreshToken()));

        return tokenResponseDto;
    }

    public TokenResponseDto reissue(TokenRequestDto tokenRequestDto) {
            return null;
//        String oldAccessToken = tokenRequestDto.getAccessToken();
//        Authentication authentication = jwtProvider.getAuthentication(oldAccessToken);
//        User user = (User) authentication.getPrincipal();
//
//        String oldRefreshToken = tokenRequestDto.getRefreshToken();
//        RefreshToken oldRedisRefreshToken = refreshTokenRedisRepository.findById(user.getUserId()).orElseThrow(CRefreshTokenExpiredException::new);
//
//        if (oldRefreshToken.equals(oldRedisRefreshToken.getRefreshToken())) {
//            String accessToken = jwtProvider.generateAccessToken(user.getUserId(), user.getRoles());
//            //refreshToken 만료임박
//            if (jwtProvider.getExpiration(oldRefreshToken) < JwtExpiration.REISSUE_EXPIRATION_TIME.getValue()) {
//                String refreshToken = jwtProvider.generateRefreshToken(user.getUserId(), user.getRoles());
//                refreshTokenRedisRepository.save(new RefreshToken(user.getUserId(), refreshToken));
//                return TokenResponseDto.builder()
//                        .grantType("bearer")
//                        .accessToken(accessToken)
//                        .refreshToken(refreshToken)
//                        .build();
//            }
//            //refreshToken 만료까지 멀었음
//            else {
//                return TokenResponseDto.builder()
//                        .grantType("bearer")
//                        .accessToken(accessToken)
//                        .refreshToken(oldRefreshToken)
//                        .build();
//            }
//        }
//        // 리프레시 토큰 불일치 에러
//        else {
//            throw new CRefreshTokenInvalidException();
//        }
    }
}
