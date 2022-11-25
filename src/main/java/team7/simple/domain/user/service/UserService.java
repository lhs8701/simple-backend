package team7.simple.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team7.simple.domain.course.dto.JoinedCourseResponseDto;
import team7.simple.domain.course.entity.Course;
import team7.simple.domain.course.service.CourseService;
import team7.simple.domain.enroll.entity.Enroll;
import team7.simple.domain.enroll.service.EnrollService;
import team7.simple.domain.record.entity.Record;
import team7.simple.domain.record.service.RecordService;
import team7.simple.domain.unit.dto.UnitHistoryResponseDto;
import team7.simple.domain.unit.entity.Unit;
import team7.simple.domain.user.dto.PasswordUpdateParam;
import team7.simple.domain.user.entity.User;
import team7.simple.domain.user.error.exception.CUserNotFoundException;
import team7.simple.domain.user.repository.UserJpaRepository;
import team7.simple.utils.RoundCalculator;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final EnrollService enrollService;
    private final RecordService recordService;
    private final CourseService courseService;
    private final RoundCalculator roundCalculator;

    private final UserJpaRepository userJpaRepository;

    /**
     * 사용자의 비밀번호를 변경합니다.
     * @param passwordUpdateParam 변경할 비밀번호
     * @param account 사용자 계정
     */
    @Transactional
    public void changePassword(String account, PasswordUpdateParam passwordUpdateParam) {
        User user = getUserByAccount(account);
        user.changePassword(passwordEncoder.encode(passwordUpdateParam.getPassword()));
    }

    /**
     * 사용자가 현재 수강중인 강좌의 목록을 반환합니다.
     * @param account 사용자 계정
     * @return 강좌 아이디, 강좌 이름, 수강 등록 날짜
     */
    @Transactional
    public List<JoinedCourseResponseDto> getJoinedCourses(String account) {
        User user = getUserByAccount(account);
        List<Enroll> enrollList = enrollService.getStudyListByUser(user);
        return enrollList.stream().map(JoinedCourseResponseDto::new).collect(Collectors.toList());
    }

    /**
     * 강좌에 대한 강의 수강 현황을 조회합니다.
     * @param account 사용자 계정
     * @param courseId 강좌 아이디
     * @return 강의 아이디, 제목, 완료 여부, 진척도
     */
    @Transactional
    public List<UnitHistoryResponseDto> getStudyHistory(String account, Long courseId) {
        User user = getUserByAccount(account);
        Course course = courseService.getCourseById(courseId);
        enrollService.getStudyByCourseAndUser(course, user);
        List<Unit> unitList = course.getUnitList();
        List<UnitHistoryResponseDto> unitHistoryList = new ArrayList<>();
        for (Unit unit : unitList) {
            boolean completed = isCompleted(user, unit);
            double progress = roundCalculator.round(recordService.getTimeline(user, unit) * 100, 0);
            unitHistoryList.add(new UnitHistoryResponseDto(unit, completed, progress));
        }

        return unitHistoryList;
    }

    private boolean isCompleted(User user, Unit unit) {
        Record record = recordService.getRecordByUnitAndUser(unit, user).orElse(null);
        if (record == null) {
            return false;
        }
        return record.isCompleted();
    }
    @Transactional
    public User getUserById(String userId) {
        return userJpaRepository.findById(userId).orElseThrow(CUserNotFoundException::new);
    }

    @Transactional
    public User getUserByAccount(String account){
        return userJpaRepository.findByAccount(account).orElseThrow(CUserNotFoundException::new);
    }
}
