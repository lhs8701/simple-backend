package team7.simple.domain.record.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team7.simple.domain.unit.entity.Unit;
import team7.simple.domain.user.entity.User;
import team7.simple.domain.record.entity.Record;

import java.util.List;
import java.util.Optional;

public interface RecordJpaRepository extends JpaRepository<Record, Long> {
    Optional<Record> findByUnit(Unit unit);
    Optional<Record> findByUnitAndUser(Unit unit, User user);

    List<Record> findAllByUnit(Unit unit);
}
