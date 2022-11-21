package team7.simple.domain.course.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team7.simple.domain.course.dto.CourseRequestDto;
import team7.simple.domain.course.dto.CourseDetailResponseDto;
import team7.simple.domain.course.dto.CourseUpdateParam;
import team7.simple.domain.course.dto.RegisterCancelRequestDto;
import team7.simple.domain.course.entity.Course;
import team7.simple.domain.study.entity.Study;
import team7.simple.domain.course.repository.CourseJpaRepository;
import team7.simple.domain.study.repository.StudyJpaRepository;
import team7.simple.domain.unit.service.UnitService;
import team7.simple.domain.user.entity.User;
import team7.simple.global.error.advice.exception.CCourseNotFoundException;
import team7.simple.global.error.advice.exception.CStudyNotFoundException;

import java.util.List;


@RequiredArgsConstructor
@Service
public class CourseService {

    private final CourseJpaRepository courseJpaRepository;
    private final StudyJpaRepository studyJpaRepository;
    private final UnitService unitService;

    @Transactional
    public Long createCourse(CourseRequestDto courseRequestDto, User instructor) {
        Course course = courseRequestDto.toEntity(instructor);
        return courseJpaRepository.save(course).getCourseId();
    }

    @Transactional
    public CourseDetailResponseDto getCourseInfo(Long courseId) {
        Course course = courseJpaRepository.findById(courseId)
                .orElseThrow(CCourseNotFoundException::new);

        int attendeeCount = 0;
        List<Study> studyList = studyJpaRepository.findAllByCourse(course).orElse(null);
        if (studyList != null){
            attendeeCount = studyList.size();
        }
        return new CourseDetailResponseDto(course, attendeeCount, unitService.getUnitThumbnailList(courseId));
    }


    @Transactional
    public void deleteCourse(Long courseId) {
        Course course = courseJpaRepository.findById(courseId)
                .orElseThrow(CCourseNotFoundException::new);

        courseJpaRepository.delete(course);
    }

    @Transactional
    public Long updateCourse(Long courseId, CourseUpdateParam courseUpdateParam) {
        Course course = courseJpaRepository.findById(courseId)
                .orElseThrow(CCourseNotFoundException::new);

        course.setTitle(courseUpdateParam.getTitle());
        course.setSubtitle(courseUpdateParam.getSubtitle());
        return courseId;
    }

    public Long register(RegisterCancelRequestDto registerCancelRequestDto, User user) {
        Long courseId = registerCancelRequestDto.getCourseId();
        Course course = courseJpaRepository.findById(courseId).orElseThrow(CCourseNotFoundException::new);
        Study study = Study.builder().course(course).user(user).build();
        return studyJpaRepository.save(study).getId();
    }

    public void cancel(RegisterCancelRequestDto registerCancelRequestDto, User user) {
        Long courseId = registerCancelRequestDto.getCourseId();
        Study study = user.getStudyList()
                .stream()
                .filter(s -> s.getCourse().getCourseId().equals(courseId))
                .findFirst()
                .orElseThrow(CStudyNotFoundException::new);

        studyJpaRepository.delete(study);
    }
}
