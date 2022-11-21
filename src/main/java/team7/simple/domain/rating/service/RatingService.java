package team7.simple.domain.rating.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team7.simple.domain.course.entity.Course;
import team7.simple.domain.rating.dto.RatingRequestDto;
import team7.simple.domain.study.repository.StudyJpaRepository;
import team7.simple.domain.unit.repository.UnitJpaRepository;
import team7.simple.domain.user.entity.User;
import team7.simple.domain.viewingrecord.entity.ViewingRecord;
import team7.simple.domain.viewingrecord.repository.ViewingRecordRedisRepository;
import team7.simple.global.error.advice.exception.*;

import java.util.List;


@Service
@Slf4j
@RequiredArgsConstructor
public class RatingService {

    private final ViewingRecordRedisRepository viewingRecordRedisRepository;
    private final UnitJpaRepository unitJpaRepository;
    private final StudyJpaRepository studyJpaRepository;

    @Transactional
    public void addRating(Long unitId, RatingRequestDto ratingRequestDto, User user) {
        Course course = unitJpaRepository.findById(unitId).orElseThrow(CUnitNotFoundException::new).getCourse();
        studyJpaRepository.findByCourseAndUser(course, user).orElseThrow(CStudyNotFoundException::new);
        ViewingRecord viewingRecord = viewingRecordRedisRepository.findByUnitIdAndUserId(unitId, user.getUserId()).orElse(null);
        if (viewingRecord == null) {
            log.error("ViewingRecord가 없습니다.");
            throw new CAccessDeniedException();
        }
        viewingRecord.setScore(ratingRequestDto.getScore());
    }

    @Transactional
    public double getAverageRatingScore(Long unitId) {
        double rating;
        List<ViewingRecord> viewingRecordList = viewingRecordRedisRepository.findAllByUnitId(unitId);
        if (viewingRecordList == null) {
            log.error("해당 강의의 ViewingRecord가 없습니다.");
            throw new CRatingNotFoundException();
        }
        double sum = 0;
        int whole = 0;
        for (ViewingRecord viewingRecord : viewingRecordList) {
            if (viewingRecord.isCheck()) {
                whole++;
                sum += viewingRecord.getScore();
            }
        }
        rating = (double) Math.round((sum / whole) * 10) / 10.0;
        return rating;
    }
}
