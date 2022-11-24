package team7.simple.domain.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team7.simple.domain.course.entity.Course;
import team7.simple.domain.course.service.CourseService;
import team7.simple.domain.record.service.RecordService;
import team7.simple.domain.study.dto.JoinedCourseResponseDto;
import team7.simple.domain.study.entity.Study;
import team7.simple.domain.study.service.EnrollService;
import team7.simple.domain.user.dto.PasswordUpdateParam;
import team7.simple.domain.user.entity.User;
import team7.simple.domain.user.repository.UserJpaRepository;
import team7.simple.domain.user.error.exception.CUserNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final EnrollService enrollService;
    private final RecordService recordService;
    private final CourseService courseService;

    private final UserJpaRepository userJpaRepository;
    public void changePassword(PasswordUpdateParam passwordUpdateParam, User user){
        user.setPassword(passwordEncoder.encode(passwordUpdateParam.getPassword()));
    }
    public User getUserById(String userId){
        return userJpaRepository.findById(userId).orElseThrow(CUserNotFoundException::new);
    }

    public Object getJoinedCourse(User user) {
        List<Study> studyList = enrollService.getStudyListByUser(user);
        List<Course> joinedCourseList = studyList.stream().map(Study::getCourse).toList();

        return joinedCourseList.stream().map(JoinedCourseResponseDto::new).collect(Collectors.toList());
    }

    public Object getStudyHistory(Long courseId, User user) {

        return null;
    }
}
