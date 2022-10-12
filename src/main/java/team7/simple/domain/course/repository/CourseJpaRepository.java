package team7.simple.domain.course.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team7.simple.domain.course.entity.Course;


public interface CourseJpaRepository extends JpaRepository<Course, Long> {
}
