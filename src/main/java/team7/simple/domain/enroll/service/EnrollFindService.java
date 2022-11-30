package team7.simple.domain.enroll.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team7.simple.domain.course.entity.Course;
import team7.simple.domain.enroll.entity.Enroll;
import team7.simple.domain.enroll.error.exception.CUserNotEnrolledException;
import team7.simple.domain.enroll.repository.EnrollJpaRepository;
import team7.simple.domain.user.entity.User;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EnrollFindService {

    private final EnrollJpaRepository enrollJpaRepository;

    public Enroll getStudyByCourseAndUser(Course course, User user) {
        return enrollJpaRepository.findByCourseAndUser(course, user).orElseThrow(CUserNotEnrolledException::new);
    }

    public List<Enroll> getStudyListByCourse(Course course) {
        return enrollJpaRepository.findAllByCourse(course);
    }

    public List<Enroll> getStudyListByUser(User user) {
        return enrollJpaRepository.findAllByUser(user);
    }
}
