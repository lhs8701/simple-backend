package team7.simple.domain.study.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team7.simple.domain.course.entity.Course;
import team7.simple.domain.study.entity.Study;
import team7.simple.domain.study.error.exception.CStudyNotFoundException;
import team7.simple.domain.study.repository.EnrollJpaRepository;
import team7.simple.domain.user.entity.User;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EnrollService {
    private final EnrollJpaRepository enrollJpaRepository;

    public boolean isUserInCourse(Course course, User user) {
        return enrollJpaRepository.findByCourseAndUser(course, user).isPresent();
    }



    public Study getStudyByCourseAndUser(Course course, User user) {
        return enrollJpaRepository.findByCourseAndUser(course, user).orElseThrow(CStudyNotFoundException::new);
    }

    public Long saveStudy(Course course, User user) {
        Study study = enrollJpaRepository.save(Study.builder()
                .course(course)
                .user(user)
                .build());
        return study.getId();
    }

    public void deleteStudy(Study study) {
        enrollJpaRepository.delete(study);
    }

    public List<Study> getStudyListByCourse(Course course) {
        return enrollJpaRepository.findAllByCourse(course);
    }

    public List<Study> getStudyListByUser(User user) {
        return enrollJpaRepository.findAllByUser(user);
    }
}
