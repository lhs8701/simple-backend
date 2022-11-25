package team7.simple.domain.rating.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team7.simple.domain.rating.entity.Rating;

public interface RatingJpaRepository extends JpaRepository<Rating, Long> {
}
