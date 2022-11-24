package team7.simple.domain.study.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team7.simple.domain.course.entity.Course;
import team7.simple.domain.study.entity.Enroll;
import team7.simple.domain.study.error.exception.CUserNotEnrolledException;
import team7.simple.domain.study.repository.EnrollJpaRepository;
import team7.simple.domain.user.entity.User;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EnrollService {
    private final EnrollJpaRepository enrollJpaRepository;

    public boolean doesEnrolled(Course course, User user) {
        return enrollJpaRepository.findByCourseAndUser(course, user).isPresent();
    }

    public Enroll getStudyByCourseAndUser(Course course, User user) {
        return enrollJpaRepository.findByCourseAndUser(course, user).orElseThrow(CUserNotEnrolledException::new);
    }

    public Long saveStudy(Course course, User user) {
        Enroll enroll = enrollJpaRepository.save(Enroll.builder()
                .course(course)
                .user(user)
                .build());
        return enroll.getId();
    }

    public void deleteStudy(Enroll enroll) {
        enrollJpaRepository.delete(enroll);
    }

    public List<Enroll> getStudyListByCourse(Course course) {
        return enrollJpaRepository.findAllByCourse(course);
    }

    public List<Enroll> getStudyListByUser(User user) {
        return enrollJpaRepository.findAllByUser(user);
    }
}
