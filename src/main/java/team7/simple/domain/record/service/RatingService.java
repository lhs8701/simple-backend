package team7.simple.domain.record.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team7.simple.domain.course.entity.Course;
import team7.simple.domain.record.dto.RatingRequestDto;
import team7.simple.domain.record.dto.RatingResponseDto;
import team7.simple.domain.record.entity.Record;
import team7.simple.domain.study.service.EnrollService;
import team7.simple.domain.unit.entity.Unit;
import team7.simple.domain.unit.service.UnitService;
import team7.simple.domain.user.entity.User;

import java.util.List;


@Service
@Slf4j
@RequiredArgsConstructor
public class RatingService {

    private final EnrollService enrollService;
    private final RecordService recordService;

    private final UnitService unitService;

    @Transactional
    public void addRating(Long unitId, RatingRequestDto ratingRequestDto, User user) {
        Unit unit = unitService.findUnitById(unitId);
        Course course = unit.getCourse();
        if (doesUserEnrollCourse(course, user)) {
            Record record = recordService.getRecordByUnitAndUser(unit, user).orElse(null);
            //        if (record == null || !record.isCompleted()) {
            //            log.error("평점을 등록하기 위해서는, 해당 강의를 끝까지 시청해야합니다.");
            //            throw new CAccessDeniedException();
            //        }
            record.setRating(ratingRequestDto.getScore());
        }
    }

    private boolean doesUserEnrollCourse(Course course, User user) {
        enrollService.getStudyByCourseAndUser(course, user);
        return true;
    }

    @Transactional
    public RatingResponseDto getAverageRatingScore(Long unitId) {
        double rating;
        Unit unit = unitService.findUnitById(unitId);
        List<Record> recordList = recordService.getRecordListByUnit(unit);
        if (recordList == null) {
            return new RatingResponseDto(0, 0);
        }
        return calculate(recordList);
    }

    private RatingResponseDto calculate(List<Record> recordList) {
        double rating;
        double sum = 0;
        int whole = 0;
        for (Record record : recordList) {
            if (record.isCompleted()) {
                whole++;
                sum += record.getRating();
            }
        }
        rating = (double) Math.round((sum / whole) * 10) / 10.0;
        return new RatingResponseDto(rating, whole);
    }
}
