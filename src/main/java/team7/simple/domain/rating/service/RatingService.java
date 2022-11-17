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
        if (viewingRecord == null || !viewingRecord.isCheck()) {
            throw new CAccessDeniedException();
        }
        viewingRecord.setScore(ratingRequestDto.getScore());
    }

    @Transactional
    public double getAverageRatingScore(Long unitId) {
        double rating = 0;
        List<ViewingRecord> viewingRecordList = viewingRecordRedisRepository.findAllByUnitId(unitId);
        if (viewingRecordList == null) {
            throw new CRatingNotFoundException();
        }
        double sum = 0;
        for (ViewingRecord viewingRecord : viewingRecordList) {
            sum += viewingRecord.getScore();
        }
        rating = Math.round((sum / viewingRecordList.size()) * 10) / 10.0;
        return rating;
    }
}
