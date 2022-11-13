package team7.simple.domain.viewingrecord.repository;

import org.springframework.data.repository.CrudRepository;
import team7.simple.domain.unit.entity.Unit;
import team7.simple.domain.user.entity.User;
import team7.simple.domain.viewingrecord.entity.ViewingRecord;

import java.util.Optional;

public interface ViewingRecordJpaRepository extends CrudRepository<ViewingRecord, Long> {
    Optional<ViewingRecord> findByUnitId(Long unitId);
    Optional<ViewingRecord> findByUnitAndUser(Unit unit, User user);
}
