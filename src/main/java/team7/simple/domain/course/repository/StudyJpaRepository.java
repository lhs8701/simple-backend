package team7.simple.domain.course.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team7.simple.domain.course.entity.Study;

public interface StudyJpaRepository extends JpaRepository<Study,Long> {
}
