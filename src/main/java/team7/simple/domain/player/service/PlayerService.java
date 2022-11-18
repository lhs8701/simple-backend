package team7.simple.domain.player.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import team7.simple.domain.auth.jwt.dto.AccessTokenResponseDto;
import team7.simple.domain.auth.jwt.entity.ActiveAccessToken;
import team7.simple.domain.auth.jwt.repository.ActiveAccessTokenRedisRepository;
import team7.simple.domain.player.dto.ExecuteRequestDto;
import team7.simple.domain.player.dto.ExecuteResponseDto;
import team7.simple.domain.player.dto.ExitRequestDto;
import team7.simple.domain.player.dto.StartRequestDto;
import team7.simple.domain.user.entity.User;
import team7.simple.domain.user.repository.UserJpaRepository;
import team7.simple.domain.viewingrecord.entity.ViewingRecord;
import team7.simple.domain.viewingrecord.repository.ViewingRecordRedisRepository;
import team7.simple.global.common.constant.ActiveStatus;
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

    static final int CONNECTION_LIMIT = 1;

    @Value("${path.front_page}")
    String PLAYER_PATH;

    /**
     * 사용자 권한을 확인한 후, 플레이어 주소를 반환한다.
     *
     * @param accessToken       어세스토큰
     * @param executeRequestDto 강좌 아이디, 강의 아이디
     * @param user              사용자
     * @return 사용자 아이디, 강좌 아이디, 강의 아이디를 쿼리 파라미터로 하는 플레이어의 주소
     */
    public ExecuteResponseDto executePlayer(String accessToken, ExecuteRequestDto executeRequestDto, User user) {
        int conflictState = ActiveStatus.NO_CONFLICT.ordinal();

        ActiveAccessToken existActiveAccessToken = activeAccessTokenRedisRepository.findByUserId(user.getUserId()).orElse(null);
        conflictState = doesConflicted(conflictState, existActiveAccessToken);
        activeAccessTokenRedisRepository.save(ActiveAccessToken.builder()
                .accessToken(accessToken)
                .userId(user.getUserId())
                .conflict(conflictState)
                .build());

        return new ExecuteResponseDto(PLAYER_PATH
                + "?userId=" + String.valueOf(user.getUserId())
                + "&courseId=" + String.valueOf(executeRequestDto.getCourseId())
                + "&unitId=" + String.valueOf(executeRequestDto.getUnitId()));
    }

    /**
     * 충돌을 확인한 후, 충돌 상태를 갱신한다.
     *
     * @param conflict               충돌 상태. NO_CONFLICTED는 충돌이 없음을 의미하고, PRE_CONFLICTED는 동일 계정을 먼저 접속했음을 의미하고, POST_CONFLICTED는 동일 계정을 후에 접속했음을 의미한다.
     * @param existActiveAccessToken 기존 이용중이던 사용자의 어세스토큰
     * @return 갱신된 충돌 상태를 반환한다.
     */
    private int doesConflicted(int conflict, ActiveAccessToken existActiveAccessToken) {
        if (existActiveAccessToken != null) {
            existActiveAccessToken.setConflict(ActiveStatus.PRE_CONFLICTED.ordinal());
            conflict = ActiveStatus.POST_CONFLICTED.ordinal();
        }
        return conflict;
    }

    public AccessTokenResponseDto start(StartRequestDto startRequestDto) {
        User user = userJpaRepository.findById(startRequestDto.getUserId()).orElseThrow(CUserNotFoundException::new);
        ActiveAccessToken token = 나중에_접속한_토큰_얻기(user);
        return new AccessTokenResponseDto(token.getAccessToken());
    }

    private ActiveAccessToken 나중에_접속한_토큰_얻기(User user) {
        List<ActiveAccessToken> activeAccessTokens = activeAccessTokenRedisRepository.findAllByUserId(user.getUserId());
        if (activeAccessTokens.size() > CONNECTION_LIMIT) {
            return activeAccessTokens.stream()
                    .filter(t -> t.getConflict() == ActiveStatus.POST_CONFLICTED.ordinal())
                    .findAny()
                    .orElseThrow(CExpiredTokenException::new);
        }
        return activeAccessTokens.get(0);
    }

    public void exit(String accessToken, ExitRequestDto exitRequestDto) {
        ViewingRecord viewingRecord = viewingRecordRedisRepository
                .findByUnitIdAndUserId(exitRequestDto.getUnitId(), exitRequestDto.getUserId())
                .orElseThrow(CUnitNotFoundException::new);
        viewingRecord.setTime(exitRequestDto.getTime());
        activeAccessTokenRedisRepository.deleteById(accessToken);
    }
}
