package team7.simple.domain.rating.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team7.simple.domain.course.entity.Course;
import team7.simple.domain.course.repository.CourseJpaRepository;
import team7.simple.domain.rating.dto.RatingRequestDto;
import team7.simple.domain.rating.entity.Rating;
import team7.simple.domain.rating.repository.RatingJpaRepository;
import team7.simple.domain.study.repository.StudyJpaRepository;
import team7.simple.domain.study.service.StudyService;
import team7.simple.domain.user.entity.User;
import team7.simple.global.error.advice.exception.CCourseNotFoundException;
import team7.simple.global.error.advice.exception.CStudyNotFoundException;

import java.util.List;


@Service
@Slf4j
@RequiredArgsConstructor
public class RatingService {
    private final RatingJpaRepository ratingJpaRepository;
    private final CourseJpaRepository courseJpaRepository;
    private final StudyService studyService;

    @Transactional
    public void addRating(RatingRequestDto ratingRequestDto, User user) {
        Course course = courseJpaRepository.findById(ratingRequestDto.getCourseId()).orElseThrow(CCourseNotFoundException::new);
        if (!studyService.isUserInCourse(course, user)) {
            throw new CStudyNotFoundException();
        }
        Rating searchedRating = ratingJpaRepository.findByCourseAndUser(course, user).orElse(null);
        if (searchedRating == null) {
            Rating rating = Rating.builder().course(course).user(user).score(ratingRequestDto.getScore()).build();
            ratingJpaRepository.save(rating);
        } else {
            searchedRating.setScore(ratingRequestDto.getScore());
        }
    }

    @Transactional
    public double getCourseAverageRatingScore(Course course) {
        double rating = 0;
        List<Rating> ratingList = ratingJpaRepository.findAllByCourse(course).orElse(null);
        if (ratingList != null) {
            double sum = 0;
            for (Rating elem : ratingList) {
                sum += elem.getScore();
            }
            rating = Math.round((sum / ratingList.size()) * 10) / 10.0;
        }
        return rating;
    }
}
