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
import team7.simple.domain.record.service.RecordService;
import team7.simple.domain.unit.entity.Unit;
import team7.simple.domain.unit.service.UnitService;
import team7.simple.domain.user.entity.User;
import team7.simple.domain.user.repository.UserJpaRepository;
import team7.simple.domain.record.entity.Record;
import team7.simple.global.common.constant.ActiveStatus;
import team7.simple.global.error.advice.exception.*;
import team7.simple.global.security.JwtProvider;

import java.util.List;


@Service
@Slf4j
@RequiredArgsConstructor
public class PlayerService {
    private final UserJpaRepository userJpaRepository;
    private final ActiveAccessTokenRedisRepository activeAccessTokenRedisRepository;
    private final UnitService unitService;
    private final RecordService recordService;

    private final JwtProvider jwtProvider;

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
        int conflictState = doesConflicted(user);
        activeAccessTokenRedisRepository.save(ActiveAccessToken.builder()
                .accessToken(accessToken)
                .userId(user.getId())
                .conflict(conflictState)
                .build());
        return new ExecuteResponseDto(PLAYER_PATH
                + "?userId=" + user.getId()
                + "&courseId=" + executeRequestDto.getCourseId()
                + "&unitId=" + executeRequestDto.getUnitId());
    }

    private int doesConflicted(User user) {
        int conflictStatus = ActiveStatus.NO_CONFLICT.ordinal();
        ActiveAccessToken existActiveAccessToken = activeAccessTokenRedisRepository.findByUserId(user.getId()).orElse(null);
        if (existActiveAccessToken != null) {
            updateConflictStatus(existActiveAccessToken, ActiveStatus.PRE_CONFLICTED.ordinal());
            conflictStatus = ActiveStatus.POST_CONFLICTED.ordinal();
        }
        return conflictStatus;
    }

    public void updateConflictStatus(ActiveAccessToken oldToken, int conflictStatus) {
        ActiveAccessToken newToken = ActiveAccessToken.builder()
                .accessToken(oldToken.getAccessToken())
                .userId(oldToken.getUserId())
                .conflict(conflictStatus)
                .expiration(oldToken.getExpiration())
                .build();
        activeAccessTokenRedisRepository.delete(oldToken);
        activeAccessTokenRedisRepository.save(newToken);
    }

    public AccessTokenResponseDto start(StartRequestDto startRequestDto) {
        User user = userJpaRepository.findById(startRequestDto.getUserId()).orElseThrow(CUserNotFoundException::new);
        ActiveAccessToken token = 나중에_접속한_토큰_얻기(user);

        return new AccessTokenResponseDto(token.getAccessToken());
    }

    private ActiveAccessToken 나중에_접속한_토큰_얻기(User user) {
        List<ActiveAccessToken> activeAccessTokens = activeAccessTokenRedisRepository.findAllByUserId(user.getId());
        if (activeAccessTokens == null) {
            throw new CUserNotActiveException();
        }

        if (activeAccessTokens.size() > CONNECTION_LIMIT) {
            return activeAccessTokens.stream()
                    .filter(t -> t.getConflict() == ActiveStatus.POST_CONFLICTED.ordinal())
                    .findAny()
                    .orElseThrow(CExpiredTokenException::new);
        }

        return activeAccessTokens.get(0);
    }

    public void exit(String accessToken, ExitRequestDto exitRequestDto) {
        User user = (User) jwtProvider.getAuthentication(accessToken).getPrincipal();
        ActiveAccessToken activeToken = activeAccessTokenRedisRepository.findById(accessToken).orElseThrow(CUserNotActiveException::new);
        Unit unit = unitService.findUnitById(exitRequestDto.getUnitId());
        Record record = recordService.getRecordByUnitAndUser(unit, user).orElse(null);
        if (record == null) {
            recordService.saveRecord(unit, user, exitRequestDto.getTime(), exitRequestDto.isCheck());
        } else {
            record.setTimeline(exitRequestDto.getTime());
        }

        ActiveAccessToken anotherToken = getAnotherUserToken(user, activeToken);
        if (anotherToken != null) {
            updateConflictStatus(anotherToken, ActiveStatus.NO_CONFLICT.ordinal());
        }

        activeAccessTokenRedisRepository.deleteById(accessToken);
    }

    private ActiveAccessToken getAnotherUserToken(User user, ActiveAccessToken activeToken) {
        if (activeToken.getConflict() == ActiveStatus.NO_CONFLICT.ordinal()) {
            return null;
        }
        if (activeToken.getConflict() == ActiveStatus.PRE_CONFLICTED.ordinal()) {
            return activeAccessTokenRedisRepository
                    .findByUserIdAndConflict(user.getId(), ActiveStatus.POST_CONFLICTED.ordinal())
                    .orElseThrow(CUserNotActiveException::new);
        }
        return activeAccessTokenRedisRepository
                .findByUserIdAndConflict(user.getId(), ActiveStatus.PRE_CONFLICTED.ordinal())
                .orElseThrow(CUserNotActiveException::new);
    }
}
