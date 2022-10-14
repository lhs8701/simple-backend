package team7.simple.domain.rating.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team7.simple.domain.course.entity.Course;
import team7.simple.domain.rating.entity.Rating;
import team7.simple.domain.user.entity.User;

import java.util.List;
import java.util.Optional;

public interface RatingJpaRepository extends JpaRepository<Rating, Long> {
    Optional<List<Rating>> findAllByCourse(Course course);

    Optional<Rating> findByCourseAndUser(Course course, User user);
}
