package team7.simple.domain.study.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team7.simple.domain.course.entity.Course;
import team7.simple.domain.study.entity.Study;
import team7.simple.domain.user.entity.User;

import java.util.List;
import java.util.Optional;

public interface EnrollJpaRepository extends JpaRepository<Study,Long> {
    List<Study> findAllByCourse(Course course);
    Optional<Study> findByCourseAndUser(Course course, User user);
    List<Study> findAllByUser(User user);
}
