package team7.simple.domain.enroll.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team7.simple.domain.course.entity.Course;
import team7.simple.domain.enroll.entity.Enroll;
import team7.simple.domain.user.entity.User;

import java.util.List;
import java.util.Optional;

public interface EnrollJpaRepository extends JpaRepository<Enroll,Long> {
    List<Enroll> findAllByCourse(Course course);
    Optional<Enroll> findByCourseAndUser(Course course, User user);
    List<Enroll> findAllByUser(User user);
}
