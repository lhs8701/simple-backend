package team7.simple.domain.rating.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team7.simple.domain.course.entity.Course;
import team7.simple.domain.enroll.error.exception.CUserNotEnrolledException;
import team7.simple.domain.enroll.service.EnrollFindService;
import team7.simple.domain.enroll.service.EnrollService;
import team7.simple.domain.rating.dto.RatingRequestDto;
import team7.simple.domain.rating.dto.RatingResponseDto;
import team7.simple.domain.rating.entity.Rating;
import team7.simple.domain.rating.repository.RatingJpaRepository;
import team7.simple.domain.record.error.exception.CRecordNotFoundException;
import team7.simple.domain.unit.entity.Unit;
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

    /**
     * 강의에 대한 평점을 등록합니다.
     * 여러번 등록할 경우, 가장 최근에 등록한 내용으로 교체됩니다.
     * @param unitId 강의 아이디
     * @param ratingRequestDto 점수, 평가 의견
     * @param user 사용자
     */
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


    /**
     * 사용자가 평점을 등록할 수 있는 권한을 가지고 있는지 확인합니다.
     * 강좌를 수강 신청하지 않았다면 예외가 발생합니다.
     * @param user
     * @param unit
     */
    private void validateAbility(User user, Unit unit) {
        Course course = unit.getCourse();
        if (!enrollService.doesEnrolled(course, user)) {
            throw new CUserNotEnrolledException();
        }
    }


    /**
     * 강의에 대한 평점을 조회합니다.
     * @param unitId 강의 아이디
     * @return 강의 평점, 평점을 매긴 사용자 수
     */
    @Transactional
    public RatingResponseDto getAverageRatingScore(Long unitId) {
        Unit unit = unitFindService.getUnitById(unitId);
        List<Rating> ratingList = unit.getRatingList();
        if (ratingList == null) {
            return new RatingResponseDto(0, 0);
        }
        return calculate(ratingList);
    }


    /**
     * 강의에 대한 평점을 계산합니다.
     * @param ratingList 강의에 대한 평점 목록
     * @return 강의 평점, 평점을 매긴 사용자 수
     */
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
