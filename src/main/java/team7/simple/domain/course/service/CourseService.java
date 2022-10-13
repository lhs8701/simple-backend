package team7.simple.domain.course.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team7.simple.domain.course.dto.CourseRequestDto;
import team7.simple.domain.course.dto.CourseResponseDto;
import team7.simple.domain.course.dto.CourseUpdateParam;
import team7.simple.domain.course.dto.RegisterCancelRequestDto;
import team7.simple.domain.course.entity.Course;
import team7.simple.domain.rating.entity.Rating;
import team7.simple.domain.rating.repository.RatingJpaRepository;
import team7.simple.domain.study.entity.Study;
import team7.simple.domain.course.repository.CourseJpaRepository;
import team7.simple.domain.study.repository.StudyJpaRepository;
import team7.simple.domain.unit.dto.UnitResponseDto;
import team7.simple.domain.unit.entity.Unit;
import team7.simple.domain.user.entity.User;
import team7.simple.domain.viewingrecord.repository.ViewingRecordJpaRepository;
import team7.simple.global.error.advice.exception.CCourseNotFoundException;
import team7.simple.global.error.advice.exception.CStudyNotFoundException;

import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class CourseService {

    private final CourseJpaRepository courseJpaRepository;
    private final StudyJpaRepository studyJpaRepository;

    private final RatingJpaRepository ratingJpaRepository;
    private final ViewingRecordJpaRepository viewingRecordJpaRepository;

    @Transactional
    public Long createCourse(CourseRequestDto courseRequestDto, User instructor) {
        Course course = courseRequestDto.toEntity(instructor);
        return courseJpaRepository.save(course).getCourseId();
    }

    @Transactional
    public CourseResponseDto getCourseInfo(Long courseId) {
        Course course = courseJpaRepository.findById(courseId)
                .orElseThrow(CCourseNotFoundException::new);

        double rating = 0;
        List<Rating> ratingList = ratingJpaRepository.findAllByCourse(course).orElse(null);
        if (ratingList != null){
            double sum = 0;
            for (Rating elem : ratingList) {
                sum += elem.getScore();
            }
            rating = sum / ratingList.size();
        }

        int attendeeCount = 0;
        List<Study> studyList = studyJpaRepository.findAllByCourse(course).orElse(null);
        if (studyList != null){
            attendeeCount = studyList.size();
        }
        return new CourseResponseDto(course,attendeeCount, rating,  getUnitList(course));
    }

    @Transactional
    public List<UnitResponseDto> getUnitList(Course course) {
        List<Unit> unitList = course.getUnitList();
        if (unitList == null)
            return null;
        return unitList.stream().map(UnitResponseDto::new).collect(Collectors.toList());
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
