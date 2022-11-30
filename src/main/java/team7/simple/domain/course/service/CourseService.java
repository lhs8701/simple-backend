package team7.simple.domain.course.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team7.simple.global.error.advice.exception.CAccessDeniedException;
import team7.simple.domain.course.dto.CourseDetailResponseDto;
import team7.simple.domain.course.dto.CourseRequestDto;
import team7.simple.domain.course.dto.CourseUpdateParam;
import team7.simple.domain.course.entity.Course;
import team7.simple.domain.course.error.exception.CAlreadyJoinedCourseException;
import team7.simple.domain.course.error.exception.CCourseNotFoundException;
import team7.simple.domain.course.repository.CourseJpaRepository;
import team7.simple.domain.enroll.entity.Enroll;
import team7.simple.domain.enroll.service.EnrollService;
import team7.simple.domain.user.entity.User;

import java.util.List;


@RequiredArgsConstructor
@Slf4j
@Service
public class CourseService {
    private final CourseJpaRepository courseJpaRepository;
    private final EnrollService enrollService;


    /**
     * 강좌를 등록합니다.
     * @param courseRequestDto 강좌 제목, 강좌 부제목
     * @param instructor 강사
     * @return 등록된 강좌 아이디
     */
    @Transactional
    public Long createCourse(CourseRequestDto courseRequestDto, User instructor) {
        Course course = courseRequestDto.toEntity(instructor);
        return courseJpaRepository.save(course).getId();
    }


    /**
     * 강좌 정보를 조회합니다.
     * @param courseId 조회할 강좌 아이디
     * @return CourseDetailResponseDto (강좌 아이디, 강좌 제목, 강좌 부제목, 강사 이름, 수강생 수)
     */
    @Transactional
    public CourseDetailResponseDto getCourseInfo(Long courseId) {
        Course course = getCourseById(courseId);
        int attendeeCount = 0;
        List<Enroll> enrollList = enrollService.getStudyListByCourse(course);
        if (enrollList != null) {
            attendeeCount = enrollList.size();
        }
        return new CourseDetailResponseDto(course, attendeeCount);
    }


    /**
     * 강좌 정보를 수정합니다.
     * @param courseId 강좌 아이디
     * @param courseUpdateParam 강좌 제목, 강좌 부제목
     * @param user 사용자
     * @return 수정된 강좌 아이디
     */
    @Transactional
    public Long updateCourse(Long courseId, CourseUpdateParam courseUpdateParam, User user) {
        Course course = getCourseById(courseId);
        if (!course.getInstructor().getAccount().equals(user.getAccount())) {
            throw new CAccessDeniedException();
        }
        course.update(courseUpdateParam.getTitle(), courseUpdateParam.getSubtitle());
        return courseId;
    }


    /**
     * 강좌를 삭제합니다.
     * @param courseId 삭제할 강좌 아이디
     * @param user 사용자
     */
    @Transactional
    public void deleteCourse(Long courseId, User user) {
        Course course = getCourseById(courseId);
        if (!course.getInstructor().getAccount().equals(user.getAccount())) {
            throw new CAccessDeniedException();
        }
        courseJpaRepository.delete(course);
    }


    /**
     * 강좌를 수정합니다. 관리자용 API입니다.
     * @param courseId 수정할 강좌 아이디
     * @param courseUpdateParam 강좌 제목, 강좌 부제목
     * @return 수정된 강좌 아이디
     */
    @Transactional
    public Long updateCourse(Long courseId, CourseUpdateParam courseUpdateParam) {
        Course course = getCourseById(courseId);
        course.update(courseUpdateParam.getTitle(), courseUpdateParam.getSubtitle());
        return courseId;
    }


    /**
     * 강좌를 삭제합니다. 관리자용 API입니다.
     * @param courseId 삭제할 강좌 아이디
     */
    @Transactional
    public void deleteCourse(Long courseId) {
        Course course = getCourseById(courseId);
        courseJpaRepository.delete(course);
    }


    /**
     * 강좌에 수강 등록합니다.
     * @param courseId 강좌 아이디
     * @param user 사용자
     */
    public void register(Long courseId, User user) {
        Course course = getCourseById(courseId);
        if (enrollService.doesEnrolled(course, user)) {
            throw new CAlreadyJoinedCourseException();
        }
        enrollService.saveStudy(course, user);
    }


    /**
     * 강좌를 수강 취소합니다.
     * @param courseId 강좌 아이디
     * @param user 사용자
     */
    public void cancel(Long courseId, User user) {
        Course course = getCourseById(courseId);
        Enroll enroll = enrollService.getStudyByCourseAndUser(course, user);
        enrollService.deleteStudy(enroll);
    }


    public Course getCourseById(Long id) {
        return courseJpaRepository.findById(id).orElseThrow(CCourseNotFoundException::new);
    }
}
