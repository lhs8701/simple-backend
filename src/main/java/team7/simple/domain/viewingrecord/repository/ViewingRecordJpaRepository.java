package team7.simple.domain.viewingrecord.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team7.simple.domain.unit.entity.Unit;
import team7.simple.domain.user.entity.User;
import team7.simple.domain.viewingrecord.entity.ViewingRecord;

import java.util.List;
import java.util.Optional;

public interface ViewingRecordJpaRepository extends JpaRepository<ViewingRecord, Long> {
    Optional<ViewingRecord> findByUnit(Unit unit);
    Optional<ViewingRecord> findByUnitAndUser(Unit unit, User user);

    List<ViewingRecord> findAllByUnit(Unit unit);
}
