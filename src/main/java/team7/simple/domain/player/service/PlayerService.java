package team7.simple.domain.player.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import team7.simple.domain.auth.basic.service.AuthService;
import team7.simple.domain.auth.jwt.entity.ActiveAccessToken;
import team7.simple.domain.auth.jwt.repository.ActiveAccessTokenRedisRepository;
import team7.simple.domain.player.dto.ExitRequestDto;
import team7.simple.domain.player.dto.StartRequestDto;
import team7.simple.domain.user.entity.User;
import team7.simple.domain.user.repository.UserJpaRepository;
import team7.simple.global.error.advice.exception.CLoginConflictException;
import team7.simple.global.error.advice.exception.CUserNotFoundException;

@Service
@RequiredArgsConstructor
public class PlayerService {
    private final UserJpaRepository userJpaRepository;
    private final ActiveAccessTokenRedisRepository activeAccessTokenRedisRepository;

    private final AuthService authService;
    @Value("${path.front_page}")
    String frontPath;
    public String executePlayer(String accessToken, StartRequestDto startRequestDto){
        User user = userJpaRepository.findById(startRequestDto.getUserId()).orElseThrow(CUserNotFoundException::new);

        return frontPath +
                "?userId=" + String.valueOf(user.getUserId()) +
                "&courseId=" + String.valueOf(startRequestDto.getCourseId()) +
                "&unitId=" + String.valueOf(startRequestDto.getUnitId());
    }

    public void start(String accessToken, StartRequestDto startRequestDto) {
        User user = userJpaRepository.findById(startRequestDto.getUserId()).orElseThrow(CUserNotFoundException::new);

        /* 중복 시청 방지 */
        ActiveAccessToken existActiveAccessToken = activeAccessTokenRedisRepository.findByUserId(user.getUserId()).orElse(null);
        if (existActiveAccessToken != null){
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
