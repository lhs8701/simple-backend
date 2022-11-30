package team7.simple.domain.record.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team7.simple.domain.record.entity.Record;
import team7.simple.domain.record.repository.RecordJpaRepository;
import team7.simple.domain.unit.entity.Unit;
import team7.simple.domain.user.entity.User;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RecordFindService {

    private final RecordJpaRepository recordJpaRepository;

    public Optional<Record> getRecordByUnitAndUserWithOptional(Unit unit, User user) {
        return recordJpaRepository.findByUnitAndUser(unit, user);
    }
}
