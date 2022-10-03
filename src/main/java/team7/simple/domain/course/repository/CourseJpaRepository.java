package team7.simple.domain.course.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team7.simple.domain.course.entity.Course;

import java.util.List;
import java.util.Optional;

public interface CourseJpaRepository extends JpaRepository<Course, Long> {

    Optional<List<Course>> findAllById(Long courseId);
}
