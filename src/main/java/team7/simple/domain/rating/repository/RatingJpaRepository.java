package team7.simple.domain.rating.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team7.simple.domain.rating.entity.Rating;
import team7.simple.domain.unit.entity.Unit;
import team7.simple.domain.user.entity.User;

import java.util.Optional;

public interface RatingJpaRepository extends JpaRepository<Rating, Long> {
    Optional<Rating> findByUnitAndUser(Unit unit, User user);
}
