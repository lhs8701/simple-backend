package team7.simple.domain.study.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team7.simple.domain.course.entity.Course;
import team7.simple.domain.study.repository.StudyJpaRepository;
import team7.simple.domain.user.entity.User;
import team7.simple.global.error.advice.exception.CStudyNotFoundException;

@Service
@RequiredArgsConstructor
public class StudyService {
    private final StudyJpaRepository studyJpaRepository;

    public boolean isUserInCourse(Course course, User user){
        return studyJpaRepository.findByCourseAndUser(course, user).isPresent();
    }
}
