package team7.simple.domain.rating.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team7.simple.domain.course.entity.Course;
import team7.simple.domain.enroll.error.exception.CUserNotEnrolledException;
import team7.simple.domain.rating.dto.RatingRequestDto;
import team7.simple.domain.rating.dto.RatingResponseDto;
import team7.simple.domain.rating.entity.Rating;
import team7.simple.domain.rating.repository.RatingJpaRepository;
import team7.simple.domain.record.entity.Record;
import team7.simple.domain.enroll.service.EnrollService;
import team7.simple.domain.record.service.RecordService;
import team7.simple.domain.unit.entity.Unit;
import team7.simple.domain.unit.service.UnitService;
import team7.simple.domain.user.entity.User;
import team7.simple.global.error.advice.exception.CAccessDeniedException;

import java.util.List;


@Service
@Slf4j
@RequiredArgsConstructor
public class RatingService {

    private final EnrollService enrollService;
    private final RecordService recordService;

    private final UnitService unitService;
    private final RatingJpaRepository ratingJpaRepository;

    @Transactional
    public void addRating(Long unitId, RatingRequestDto ratingRequestDto, User user) {
        Unit unit = unitService.getUnitById(unitId);
        Course course = unit.getCourse();
        if (!doesUserEnrollCourse(course, user)) {
            throw new CUserNotEnrolledException();
        }
        Record record = recordService.getRecordByUnitAndUser(unit, user).orElse(null);
        if (record == null || !record.isCompleted()) {
            log.error("평점을 등록하기 위해서는, 해당 강의를 끝까지 시청해야합니다.");
            throw new CAccessDeniedException();
        }
        Rating rating = Rating.builder()
                .content(ratingRequestDto.getContent())
                .score(ratingRequestDto.getScore())
                .build();
        ratingJpaRepository.save(rating);
    }

    private boolean doesUserEnrollCourse(Course course, User user) {
        enrollService.getStudyByCourseAndUser(course, user);
        return true;
    }

    @Transactional
    public RatingResponseDto getAverageRatingScore(Long unitId) {
        List<Rating> ratingList = unitService.getUnitById(unitId).getRatingList();
        if (ratingList == null) {
            return new RatingResponseDto(0, 0);
        }
        return calculate(ratingList);
    }

    private RatingResponseDto calculate(List<Rating> ratingList) {
        double sum = 0;
        int whole = 0;
        for (Rating rating : ratingList) {
            whole++;
            sum += rating.getScore();
        }
        double averageScore = (double) Math.round((sum / whole) * 10) / 10.0;
        return new RatingResponseDto(averageScore, whole);
    }
}
