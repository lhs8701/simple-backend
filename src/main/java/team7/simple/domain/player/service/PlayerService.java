package team7.simple.domain.player.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import team7.simple.domain.auth.jwt.entity.ActiveAccessToken;
import team7.simple.domain.auth.jwt.repository.ActiveAccessTokenRedisRepository;
import team7.simple.domain.player.dto.ExecuteRequestDto;
import team7.simple.domain.player.dto.ExitRequestDto;
import team7.simple.domain.player.dto.StartRequestDto;
import team7.simple.domain.user.entity.User;
import team7.simple.domain.user.repository.UserJpaRepository;
import team7.simple.domain.viewingrecord.entity.ViewingRecord;
import team7.simple.domain.viewingrecord.repository.ViewingRecordRedisRepository;
import team7.simple.global.error.advice.exception.*;

import java.net.*;
import java.util.List;


@Service
@Slf4j
@RequiredArgsConstructor
public class PlayerService {
    private final UserJpaRepository userJpaRepository;
    private final ActiveAccessTokenRedisRepository activeAccessTokenRedisRepository;

    private final ViewingRecordRedisRepository viewingRecordRedisRepository;

    @Value("${path.front_page}")
    String PLAYER_PATH;
//    String PLAYER_PATH = "http://www.naver.com";

    public URI executePlayer(String accessToken, ExecuteRequestDto executeRequestDto, User user) throws URISyntaxException {
        int conflict = 0;
        ActiveAccessToken existActiveAccessToken = activeAccessTokenRedisRepository.findByUserId(user.getUserId()).orElse(null);
        //중복로그인인 경우
        if (existActiveAccessToken != null) {
            existActiveAccessToken.setConflict(1);
            conflict = 2;
        }
        activeAccessTokenRedisRepository.save(ActiveAccessToken.builder()
                .accessToken(accessToken)
                .userId(user.getUserId())
                .conflict(conflict)
                .build());

        return new URI (PLAYER_PATH +
                "?userId=" + String.valueOf(user.getUserId()) +
                "&unitId=" + String.valueOf(executeRequestDto.getUnitId()));
    }

    public String start(StartRequestDto startRequestDto) {
        /* 중복 시청 방지 */
        User user = userJpaRepository.findById(startRequestDto.getUserId()).orElseThrow(CUserNotFoundException::new);
        ActiveAccessToken token;
        List<ActiveAccessToken> activeAccessTokens = activeAccessTokenRedisRepository.findAllByUserId(user.getUserId());
        if (activeAccessTokens.size() >= 2){
             token = activeAccessTokens.stream().filter(t -> t.getConflict() == 2).findAny().orElseThrow(CExpiredTokenException::new);
        }
        else{
            token = activeAccessTokens.get(0);
        }
        return token.getAccessToken();
    }

    public void exit(String accessToken, ExitRequestDto exitRequestDto) {
        ViewingRecord viewingRecord = viewingRecordRedisRepository.findByUnitId(exitRequestDto.getUnitId()).orElseThrow(CUnitNotFoundException::new);
        viewingRecord.setTime(exitRequestDto.getTime());
        activeAccessTokenRedisRepository.deleteById(accessToken);
    }

}
