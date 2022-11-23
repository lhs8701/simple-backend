package team7.simple.domain.player.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import team7.simple.domain.auth.error.exception.CExpiredTokenException;
import team7.simple.domain.auth.jwt.dto.AccessTokenResponseDto;
import team7.simple.domain.auth.jwt.entity.ActiveAccessToken;
import team7.simple.domain.auth.jwt.repository.ActiveAccessTokenRedisRepository;
import team7.simple.domain.auth.service.ConflictService;
import team7.simple.domain.player.dto.ExecuteRequestDto;
import team7.simple.domain.player.dto.ExecuteResponseDto;
import team7.simple.domain.player.dto.ExitRequestDto;
import team7.simple.domain.player.dto.StartRequestDto;
import team7.simple.domain.record.service.RecordService;
import team7.simple.domain.unit.entity.Unit;
import team7.simple.domain.unit.service.UnitService;
import team7.simple.domain.user.entity.User;
import team7.simple.domain.user.error.exception.CUserNotActiveException;
import team7.simple.domain.record.entity.Record;
import team7.simple.domain.user.service.UserService;
import team7.simple.global.common.constant.ActiveStatus;
import team7.simple.global.error.advice.exception.CWrongApproach;
import team7.simple.global.security.JwtProvider;

import java.util.List;


@Service
@Slf4j
@RequiredArgsConstructor
public class PlayerService {
    private final UserService userService;
    private final ActiveAccessTokenRedisRepository activeAccessTokenRedisRepository;
    private final UnitService unitService;
    private final RecordService recordService;

    private final JwtProvider jwtProvider;

    private final ConflictService conflictService;

    static final int CONNECTION_LIMIT = 1;

    @Value("${path.front_page}")
    String PLAYER_PATH;

    /**
     * 사용자 권한을 확인한 후, 플레이어 주소를 반환한다. 이 때, redis 저장소에 충돌 상태를 기록한 Active 토큰이 생성된다.
     * 토큰의 유효성을 나타내는 valid가 false로 기록된다.
     * @param accessToken       어세스토큰
     * @param executeRequestDto 강좌 아이디, 강의 아이디
     * @param user              사용자
     * @return 사용자 아이디, 강좌 아이디, 강의 아이디를 쿼리 파라미터로 하는 플레이어의 주소
     */
    public ExecuteResponseDto executePlayer(String accessToken, ExecuteRequestDto executeRequestDto, User user) {
        activeAccessTokenRedisRepository.save(ActiveAccessToken.builder()
                .accessToken(accessToken)
                .userId(user.getId())
                .conflict(conflictService.doesConflicted(user))
                .valid(false)
                .build());

        return new ExecuteResponseDto(PLAYER_PATH
                + "?userId=" + user.getId()
                + "&courseId=" + executeRequestDto.getCourseId()
                + "&unitId=" + executeRequestDto.getUnitId());
    }

    /**
     * 사용자 id를 통해 가장 최근에 접속한 Active 토큰 하나를 리턴한다.
     * initPlayer를 거치며 Active 토큰의 valid 속성이 true로 설정된다.
     * @param startRequestDto userId: 사용자 id
     * @return 가장 최근에 접속한 Active 토큰
     */
    public AccessTokenResponseDto initPlayer(StartRequestDto startRequestDto) {
        User user = userService.getUserById(startRequestDto.getUserId());
        ActiveAccessToken token = getLatestActiveToken(user);

        return new AccessTokenResponseDto(token.getAccessToken());
    }

    private ActiveAccessToken getLatestActiveToken(User user) {
        ActiveAccessToken activeAccessToken = activeAccessTokenRedisRepository
                .findByUserIdAndValid(user.getId(), false)
                .orElseThrow(CWrongApproach::new);

        updateValid(activeAccessToken, true);
        return activeAccessToken;
    }

    private void updateValid(ActiveAccessToken token, boolean valid) {
        ActiveAccessToken newToken = ActiveAccessToken.builder()
                .accessToken(token.getAccessToken())
                .userId(token.getUserId())
                .conflict(token.getConflict())
                .valid(valid)
                .expiration(token.getExpiration())
                .build();
        activeAccessTokenRedisRepository.delete(token);
        activeAccessTokenRedisRepository.save(newToken);
    }


    public void exitPlayer(String accessToken, ExitRequestDto exitRequestDto) {
        User user = (User) jwtProvider.getAuthentication(accessToken).getPrincipal();
        ActiveAccessToken activeToken = activeAccessTokenRedisRepository.findById(accessToken).orElseThrow(CUserNotActiveException::new);

        setCurrentRecord(exitRequestDto, user);
        ActiveAccessToken anotherToken = getAnotherUserToken(user, activeToken);
        if (anotherToken != null) {
            conflictService.updateConflictStatus(anotherToken, ActiveStatus.NO_CONFLICT.ordinal());
        }

        activeAccessTokenRedisRepository.deleteById(accessToken);
    }

    private void setCurrentRecord(ExitRequestDto exitRequestDto, User user) {
        Unit unit = unitService.findUnitById(exitRequestDto.getUnitId());
        Record record = recordService.getRecordByUnitAndUser(unit, user).orElse(null);

        if (record == null) {
            recordService.saveRecord(unit, user, exitRequestDto.getTime(), exitRequestDto.isCheck());
        } else {
            record.setTimeline(exitRequestDto.getTime());
        }
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
