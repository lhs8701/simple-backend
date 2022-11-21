package team7.simple.domain.record.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team7.simple.domain.record.dto.RecordUpdateParam;
import team7.simple.domain.unit.entity.Unit;
import team7.simple.domain.user.entity.User;
import team7.simple.domain.record.entity.Record;
import team7.simple.domain.record.repository.RecordJpaRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RecordService {

    private final RecordJpaRepository recordJpaRepository;


    public Long saveRecord(Unit unit, User user, double timeline, boolean completed) {
        Record record = recordJpaRepository.save(Record.builder()
                .unit(unit)
                .user(user)
                .timeline(timeline)
                .completed(completed)
                .build());
        return record.getId();
    }

    public Optional<Record> getRecordByUnitAndUser(Unit unit, User user) {
        return recordJpaRepository.findByUnitAndUser(unit, user);
    }

    public List<Record> getRecordListByUnit(Unit unit) {
        return recordJpaRepository.findAllByUnit(unit);
    }


    public double getTimeline(User user, Unit unit) {
        Record record = recordJpaRepository.findByUnitAndUser(unit, user).orElse(null);
        if (record == null) {
            return 0;
        }
        return record.getTimeline();
    }
}
