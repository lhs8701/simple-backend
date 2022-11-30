package team7.simple.domain.rating.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team7.simple.domain.rating.entity.Rating;
import team7.simple.domain.rating.repository.RatingJpaRepository;
import team7.simple.domain.unit.entity.Unit;
import team7.simple.domain.user.entity.User;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RatingFindService {

    private final RatingJpaRepository ratingJpaRepository;

    public Optional<Rating> getRatingByUnitAndUserWithOptional(Unit unit, User user){
        return ratingJpaRepository.findByUnitAndUser(unit, user);
    }
}
