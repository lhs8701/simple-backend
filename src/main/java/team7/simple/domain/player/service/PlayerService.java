package team7.simple.domain.player.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import team7.simple.domain.auth.jwt.entity.ActiveAccessToken;
import team7.simple.domain.auth.jwt.repository.ActiveAccessTokenRedisRepository;
import team7.simple.domain.player.dto.ExecuteRequestDto;
import team7.simple.domain.player.dto.ExitRequestDto;
import team7.simple.domain.player.dto.StartRequestDto;
import team7.simple.domain.user.entity.User;
import team7.simple.domain.user.repository.UserJpaRepository;
import team7.simple.global.common.ConstValue;
import team7.simple.global.error.advice.exception.CIllegalArgumentException;
import team7.simple.global.error.advice.exception.CLoginConflictException;
import team7.simple.global.error.advice.exception.CUserNotFoundException;

import java.net.URI;
import java.net.URISyntaxException;


@Service
@Slf4j
@RequiredArgsConstructor
public class PlayerService {
    private final UserJpaRepository userJpaRepository;
    private final ActiveAccessTokenRedisRepository activeAccessTokenRedisRepository;

    @Value("${path.front_page}")
    String PLAYER_PATH;
//    String PLAYER_PATH = "http://www.naver.com";

    public HttpHeaders executePlayer(String accessToken, ExecuteRequestDto executeRequestDto)  {
        User user = userJpaRepository.findById(executeRequestDto.getUserId()).orElseThrow(CUserNotFoundException::new);
        try {
            URI redirectUri = new URI(PLAYER_PATH);
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.set(ConstValue.JWT_HEADER, accessToken);
            httpHeaders.set(ConstValue.UNIT_HEADER, String.valueOf(executeRequestDto.getUnitId()));
            httpHeaders.setLocation(redirectUri);
            return httpHeaders;
        }
        catch(URISyntaxException e){
            log.info(e.getMessage());
            throw new CIllegalArgumentException();
        }
    }

    public void start(String accessToken, StartRequestDto startRequestDto, User user) {
        /* 중복 시청 방지 */
        ActiveAccessToken existActiveAccessToken = activeAccessTokenRedisRepository.findByUserId(user.getUserId()).orElse(null);
        if (existActiveAccessToken != null) {
            if (startRequestDto.isForced()) {
                activeAccessTokenRedisRepository.deleteById(existActiveAccessToken.getAccessToken());
            } else {
                throw new CLoginConflictException();
            }
        }
        activeAccessTokenRedisRepository.save(new ActiveAccessToken(accessToken, user.getUserId()));
    }

    public void exit(String accessToken, ExitRequestDto exitRequestDto) {
        activeAccessTokenRedisRepository.deleteById(accessToken);
    }


}
