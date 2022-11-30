package team7.simple.domain.rating.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team7.simple.domain.course.entity.Course;
import team7.simple.domain.enroll.error.exception.CUserNotEnrolledException;
import team7.simple.domain.enroll.service.EnrollService;
import team7.simple.domain.rating.dto.RatingRequestDto;
import team7.simple.domain.rating.dto.RatingResponseDto;
import team7.simple.domain.rating.entity.Rating;
import team7.simple.domain.rating.repository.RatingJpaRepository;
import team7.simple.domain.record.error.exception.CRecordNotFoundException;
import team7.simple.domain.unit.entity.Unit;
import team7.simple.domain.unit.error.exception.CUnitNotFoundException;
import team7.simple.domain.unit.repository.UnitJpaRepository;
import team7.simple.domain.unit.service.UnitFindService;
import team7.simple.domain.user.entity.User;

import java.util.List;


@Service
@Slf4j
@RequiredArgsConstructor
public class RatingService {

    private final EnrollService enrollService;

    private final UnitFindService unitFindService;
    private final RatingJpaRepository ratingJpaRepository;
    private final RatingFindService ratingFindService;

    @Transactional
    public void addRating(Long unitId, RatingRequestDto ratingRequestDto, User user) {
        Unit unit = unitFindService.getUnitById(unitId);
        validateAbility(user, unit);
        Rating rating = ratingFindService.getRatingByUnitAndUserWithOptional(unit,user).orElse(null);
        if (rating != null){
            rating.update(ratingRequestDto.getScore(), ratingRequestDto.getComment());
            return;
        }

        ratingJpaRepository.save(Rating.builder()
                .comment(ratingRequestDto.getComment())
                .score(ratingRequestDto.getScore())
                .unit(unit)
                .user(user)
                .build());
    }

    private void validateAbility(User user, Unit unit) {
        Course course = unit.getCourse();
        if (!doesUserEnrollCourse(course, user)) {
            throw new CUserNotEnrolledException();
        }
        ratingFindService.getRatingByUnitAndUserWithOptional(unit, user).orElseThrow(CRecordNotFoundException::new);
    }

    private boolean doesUserEnrollCourse(Course course, User user) {
        enrollService.getStudyByCourseAndUser(course, user);
        return true;
    }

    @Transactional
    public RatingResponseDto getAverageRatingScore(Long unitId) {
        Unit unit = unitFindService.getUnitById(unitId);
        List<Rating> ratingList = unit.getRatingList();
        if (ratingList == null) {
            return new RatingResponseDto(0, 0);
        }
        return calculate(ratingList);
    }

    private RatingResponseDto calculate(List<Rating> ratingList) {
        double sum = 0;
        int whole = ratingList.size();
        for (Rating rating : ratingList) {
            sum += rating.getScore();
        }
        double averageScore = (double) Math.round((sum / whole) * 10) / 10.0;
        return new RatingResponseDto(averageScore, whole);
    }
}
