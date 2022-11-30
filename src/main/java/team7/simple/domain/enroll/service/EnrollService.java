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
public class EnrollService {
    private final EnrollJpaRepository enrollJpaRepository;

    /**
     * 사용자가 해당 강좌를 수강하고 있는지 확인합니다.
     * @param course 강좌
     * @param user 사용자
     * @return 수강 여부 True/False
     */
    public boolean doesEnrolled(Course course, User user) {
        return enrollJpaRepository.findByCourseAndUser(course, user).isPresent();
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
}
