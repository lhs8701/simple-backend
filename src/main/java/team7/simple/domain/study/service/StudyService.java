package team7.simple.domain.study.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team7.simple.domain.course.entity.Course;
import team7.simple.domain.study.entity.Study;
import team7.simple.domain.study.error.exception.CStudyNotFoundException;
import team7.simple.domain.study.repository.StudyJpaRepository;
import team7.simple.domain.user.entity.User;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudyService {
    private final StudyJpaRepository studyJpaRepository;

    public boolean isUserInCourse(Course course, User user) {
        return studyJpaRepository.findByCourseAndUser(course, user).isPresent();
    }

    public List<Study> getStudyListByCourse(Course course) {
        return studyJpaRepository.findAllByCourse(course);
    }

    public Study getStudyByCourseAndUser(Course course, User user) {
        return studyJpaRepository.findByCourseAndUser(course, user).orElseThrow(CStudyNotFoundException::new);
    }

    public Long saveStudy(Course course, User user) {
        Study study = studyJpaRepository.save(Study.builder()
                .course(course)
                .user(user)
                .build());
        return study.getId();
    }

    public void deleteStudy(Study study) {
        studyJpaRepository.delete(study);
    }
}
