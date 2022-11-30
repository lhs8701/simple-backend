package team7.simple.domain.record.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team7.simple.domain.course.entity.Course;
import team7.simple.domain.record.dto.RecordUpdateParam;
import team7.simple.domain.record.error.exception.CRecordNotFoundException;
import team7.simple.domain.unit.entity.Unit;
import team7.simple.domain.user.entity.User;
import team7.simple.domain.record.entity.Record;
import team7.simple.domain.record.repository.RecordJpaRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RecordService {

    private final RecordFindService recordFindService;

    private final RecordJpaRepository recordJpaRepository;


    public double getTimeline(User user, Unit unit) {
        Record record = recordFindService.getRecordByUnitAndUser(unit, user).orElse(null);
        if (record == null) {
            return 0;
        }
        return record.getTimeline();
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
