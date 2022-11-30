package team7.simple.domain.record.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team7.simple.domain.unit.entity.Unit;
import team7.simple.domain.user.entity.User;
import team7.simple.domain.record.entity.Record;
import team7.simple.domain.record.repository.RecordJpaRepository;

@Service
@RequiredArgsConstructor
public class RecordService {

    private final RecordFindService recordFindService;
    private final RecordJpaRepository recordJpaRepository;


    /**
     * 해당 강의의 마지막 시청 시간대를 반환합니다.
     * @param user 사용자
     * @param unit 강의
     * @return 강의의 마지막 시청 시간대
     */
    public double getTimeline(User user, Unit unit) {
        Record record = recordFindService.getRecordByUnitAndUserWithOptional(unit, user).orElse(null);
        if (record == null) {
            return 0;
        }
        return record.getTimeline();
    }

    /**
     * 사용자가 해당 강의를 끝까지 수강했는지 확인합니다.
     * @param user 사용자
     * @param unit 강의
     * @return 수강 완료 여부 (True/False)
     */
    public boolean isCompleted(User user, Unit unit) {
        Record record = recordFindService.getRecordByUnitAndUserWithOptional(unit, user).orElse(null);
        if (record == null) {
            return false;
        }
        return record.isCompleted();
    }


    public void saveRecord(Unit unit, User user, double recordTime, boolean complete) {
        Record record = Record.builder()
                .unit(unit)
                .user(user)
                .timeline(recordTime)
                .completed(complete)
                .build();
        recordJpaRepository.save(record);
    }
}
