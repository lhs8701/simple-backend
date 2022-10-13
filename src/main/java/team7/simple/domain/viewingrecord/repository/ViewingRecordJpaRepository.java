package team7.simple.domain.viewingrecord.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team7.simple.domain.viewingrecord.entity.ViewingRecord;

public interface ViewingRecordJpaRepository extends JpaRepository<ViewingRecord, Long> {
}
