package team7.simple.domain.course.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team7.simple.domain.course.dto.CourseDetailResponseDto;
import team7.simple.domain.course.dto.CourseRequestDto;
import team7.simple.domain.course.dto.CourseUpdateParam;
import team7.simple.domain.course.dto.RegisterCancelRequestDto;
import team7.simple.domain.course.entity.Course;
import team7.simple.domain.course.repository.CourseJpaRepository;
import team7.simple.domain.study.entity.Study;
import team7.simple.domain.study.service.StudyService;
import team7.simple.domain.unit.service.UnitService;
import team7.simple.domain.user.entity.User;
import team7.simple.global.error.advice.exception.CCourseNotFoundException;

import java.util.List;


@RequiredArgsConstructor
@Service
public class CourseService {
    private final CourseJpaRepository courseJpaRepository;
    private final CourseService courseService;
    private final StudyService studyService;
    private final UnitService unitService;

    @Transactional
    public Long createCourse(CourseRequestDto courseRequestDto, User instructor) {
        Course course = courseRequestDto.toEntity(instructor);
        return courseJpaRepository.save(course).getCourseId();
    }

    @Transactional
    public CourseDetailResponseDto getCourseInfo(Long courseId) {
        Course course = courseService.getCourseById(courseId);
        int attendeeCount = 0;
        List<Study> studyList = studyService.getStudyListByCourse(course);
        if (studyList != null){
            attendeeCount = studyList.size();
        }
        return new CourseDetailResponseDto(course, attendeeCount, unitService.getUnitThumbnailList(courseId));
    }


    @Transactional
    public void deleteCourse(Long courseId) {
        Course course = courseService.getCourseById(courseId);
        courseJpaRepository.delete(course);
    }

    @Transactional
    public Long updateCourse(Long courseId, CourseUpdateParam courseUpdateParam) {
        Course course = courseService.getCourseById(courseId);
        course.setTitle(courseUpdateParam.getTitle());
        course.setSubtitle(courseUpdateParam.getSubtitle());
        return courseId;
    }

    public Long register(RegisterCancelRequestDto registerCancelRequestDto, User user) {
        Long courseId = registerCancelRequestDto.getCourseId();
        Course course = courseService.getCourseById(courseId);
        return studyService.saveStudy(course, user);
    }

    public void cancel(RegisterCancelRequestDto registerCancelRequestDto, User user) {
        Long courseId = registerCancelRequestDto.getCourseId();
        Course course = courseService.getCourseById(courseId);
        Study study = studyService.getStudyByCourseAndUser(course, user);
        studyService.deleteStudy(study);
    }

    public Course getCourseById(Long id){
        return courseJpaRepository.findById(id).orElseThrow(CCourseNotFoundException::new);
    }
}
