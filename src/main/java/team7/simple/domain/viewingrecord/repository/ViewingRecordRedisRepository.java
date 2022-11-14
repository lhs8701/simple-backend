package team7.simple.domain.viewingrecord.repository;

import org.springframework.data.repository.CrudRepository;
import team7.simple.domain.viewingrecord.entity.ViewingRecord;

import java.util.List;
import java.util.Optional;

public interface ViewingRecordRedisRepository extends CrudRepository<ViewingRecord, Long> {
    Optional<ViewingRecord> findByUnitId(Long unitId);
    Optional<ViewingRecord> findByUnitIdAndUserId(Long unitId, String userId);

    List<ViewingRecord> findAllByUnitId(Long unitId);
}
