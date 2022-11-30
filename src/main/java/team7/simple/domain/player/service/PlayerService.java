package team7.simple.domain.player.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team7.simple.domain.auth.jwt.dto.AccessTokenResponseDto;
import team7.simple.domain.auth.jwt.entity.ActiveAccessToken;
import team7.simple.domain.auth.jwt.repository.ActiveAccessTokenRedisRepository;
import team7.simple.domain.auth.service.ConflictService;
import team7.simple.domain.player.dto.ExecuteRequestDto;
import team7.simple.domain.player.dto.ExecuteResponseDto;
import team7.simple.domain.player.dto.ExitRequestDto;
import team7.simple.domain.player.dto.StartRequestDto;
import team7.simple.domain.record.entity.Record;
import team7.simple.domain.record.service.RecordService;
import team7.simple.domain.unit.dto.UnitPlayRequestDto;
import team7.simple.domain.unit.dto.UnitPlayResponseDto;
import team7.simple.domain.unit.entity.Unit;
import team7.simple.domain.unit.error.exception.CUnitNotFoundException;
import team7.simple.domain.unit.service.UnitService;
import team7.simple.domain.user.entity.User;
import team7.simple.domain.user.error.exception.CUserNotActiveException;
import team7.simple.domain.user.service.UserService;
import team7.simple.global.common.constant.ActiveStatus;
import team7.simple.global.security.JwtProvider;
import team7.simple.infra.hls.service.HlsService;


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
    private final HlsService hlsService;

    @Value("${path.front_page}")
    String PLAYER_PATH;


    /**
     * 사용자 권한을 확인한 후, 플레이어 주소를 반환합니다.
     * 레디스 저장소에는 충돌 상태를 기록한 레디스 액세스 토큰이 생성됩니다.
     * 생성 당시에는 토큰의 유효성을 나타내는 valid값이 false로 기록됩니다.
     *
     * @param accessToken       액세스 토큰
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
     * 사용자 id를 통해 가장 최근에 접속한 레디스 액티브 토큰 하나를 리턴합니다.
     * 레디스 액티브 토큰의 valid 속성이 true로 설정됩니다.
     * @param startRequestDto 사용자 아이디
     * @return 가장 최근에 접속한 레디스 액티브 토큰
     */
    public AccessTokenResponseDto initPlayer(StartRequestDto startRequestDto) {
        User user = userService.getUserById(startRequestDto.getUserId());
        ActiveAccessToken token = conflictService.getLatestActiveToken(user);

        return new AccessTokenResponseDto(token.getAccessToken());
    }


    /**
     * 강의를 이동합니다.
     * 현재 시청 중인 강의의 재생 정보를 갱신하고, 이동할 강의의 재생 정보를 불러옵니다.
     * @param unitId 이동할 강의 아이디
     * @param unitPlayRequestDto 현재 강의 아이디, 현재 강의 재생 시각, 현재 강의 시청 완료 여부
     * @param user 사용자
     * @return 이동할 강의 아이디, 이동할 강의 제목, 이동할 강의 HLS 파일 경로, 이동할 강의 마지막 재생 시각
     */
    @Transactional
    public UnitPlayResponseDto playUnit(Long unitId, UnitPlayRequestDto unitPlayRequestDto, User user) {

        if (unitPlayRequestDto.getCurrentUnitId() != -1) {
            setCurrentUnitRecord(unitPlayRequestDto, user);
        }
        Unit nextUnit = unitService.getUnitById(unitId);

        return UnitPlayResponseDto.builder()
                .unitId(nextUnit.getId())
                .title(nextUnit.getTitle())
                .fileUrl(hlsService.getHlsFileUrl(nextUnit.getVideo()))
                .time(recordService.getTimeline(user, nextUnit))
                .build();
    }


    /**
     * 로컬 환경에서 강의를 재생하기 위한 테스트용 API입니다.
     * @param unitId 강의 아이디
     * @param unitPlayRequestDto 현재 강의 아이디, 현재 강의 재생 시각, 현재 강의 시청 완료 여부
     * @param user 사용자
     * @return 이동할 강의 아이디, 이동할 강의 제목, 이동할 강의 HLS 파일 경로, 이동할 강의 마지막 재생 시각
     */
    public UnitPlayResponseDto playUnitInLocal(Long unitId, UnitPlayRequestDto unitPlayRequestDto, User user) {

        if (unitPlayRequestDto.getCurrentUnitId() != -1) {
            setCurrentUnitRecord(unitPlayRequestDto, user);
        }
        Unit nextUnit = unitService.getUnitById(unitId);

        return UnitPlayResponseDto.builder()
                .unitId(nextUnit.getId())
                .title(nextUnit.getTitle())
                .fileUrl("testHlsFileUrl")
                .time(recordService.getTimeline(user, nextUnit))
                .build();
    }


    /**
     * 현재 시청중이던 강의의 재생 정보를 저장합니다.
     * 만약 시청 기록이 없다면, 새로 생성합니다.
     * @param unitPlayRequestDto 현재 강의 아이디, 현재 강의 재생 시각, 현재 강의 시청 완료 여부
     * @param user 사용자
     */
    private void setCurrentUnitRecord(UnitPlayRequestDto unitPlayRequestDto, User user) {
        Unit unit = unitService.getUnitById(unitPlayRequestDto.getCurrentUnitId());
        Record record = recordService.getRecordByUnitAndUser(unit, user).orElse(null);

        if (record == null) {
            recordService.saveRecord(unit, user, unitPlayRequestDto.getRecordTime(), unitPlayRequestDto.isComplete());
            return;
        }
        updateExistingRecord(unitPlayRequestDto, record);
    }


    /**
     * 현재 시청중이던 강의의 재생 정보를 갱신합니다.
     * @param unitPlayRequestDto 현재 강의 아이디, 현재 강의 재생 시각, 현재 강의 시청 완료 여부
     * @param record 갱신할 시청 기록
     */
    private void updateExistingRecord(UnitPlayRequestDto unitPlayRequestDto, Record record) {
        record.setTimeline(unitPlayRequestDto.getRecordTime());
        if (unitPlayRequestDto.isComplete() && !record.isCompleted()) {
            record.setCompleted(true);
        }
    }


    /**
     * 플레이어를 종료합니다.
     * 현재 시청중이던 강의의 재생 정보를 저장하고, 해당 레디스 액티브 토큰을 제거합니다.
     * @param accessToken 액세스 토큰
     * @param exitRequestDto 현재 강의 아이디, 현재 강의 재생 시각, 현재 강의 시청 완료 여부
     */
    public void exitPlayer(String accessToken, ExitRequestDto exitRequestDto) {
        User user = (User) jwtProvider.getAuthentication(accessToken).getPrincipal();
        ActiveAccessToken activeToken = activeAccessTokenRedisRepository.findById(accessToken).orElseThrow(CUserNotActiveException::new);

        setCurrentRecord(exitRequestDto, user);
        ActiveAccessToken anotherToken = conflictService.getAnotherActiveToken(user, activeToken);
        if (anotherToken != null) {
            conflictService.updateConflictStatus(anotherToken, ActiveStatus.NO_CONFLICT.ordinal());
        }

        activeAccessTokenRedisRepository.deleteById(accessToken);
    }


    private void setCurrentRecord(ExitRequestDto exitRequestDto, User user) {
        Unit unit = unitService.getUnitById(exitRequestDto.getUnitId());
        Record record = recordService.getRecordByUnitAndUser(unit, user).orElse(null);

        if (record == null) {
            recordService.saveRecord(unit, user, exitRequestDto.getTime(), exitRequestDto.isCheck());
        } else {
            record.setTimeline(exitRequestDto.getTime());
        }
    }
}
