package team7.simple.domain.course.service;

import lombok.RequiredArgsConstructor;
import org.springframework.expression.spel.ast.NullLiteral;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team7.simple.domain.course.dto.CourseRequestDto;
import team7.simple.domain.course.dto.CourseResponseDto;
import team7.simple.domain.course.dto.CourseUpdateParam;
import team7.simple.domain.course.entity.Course;
import team7.simple.domain.course.repository.CourseJpaRepository;
import team7.simple.domain.unit.dto.UnitResponseDto;
import team7.simple.domain.unit.entity.Unit;
import team7.simple.global.error.advice.exception.CCourseNotFoundException;

import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class CourseService {

    private final CourseJpaRepository courseJpaRepository;

    @Transactional
    public CourseResponseDto getCourse(Long courseId) {
        Course course = courseJpaRepository.findById(courseId)
                .orElseThrow(CCourseNotFoundException::new);
        return new CourseResponseDto(course);
    }

    @Transactional
    public Long create(CourseRequestDto courseRequestDto) {
        Long userId = courseRequestDto.getUserId();

        Course course = courseRequestDto.toEntity();
        return courseJpaRepository.save(course).getCourseId();
    }

    @Transactional
    public void deleteCourse(Long courseId) {
        Course course = courseJpaRepository.findById(courseId)
                .orElseThrow(CCourseNotFoundException::new);

        courseJpaRepository.delete(course);
    }

    @Transactional
    public Long update(Long courseId, CourseUpdateParam courseUpdateParam) {
        Course course = courseJpaRepository.findById(courseId)
                .orElseThrow(CCourseNotFoundException::new);

        course.setTitle(courseUpdateParam.getTitle());
        course.setSubtitle(courseUpdateParam.getSubtitle());
        return courseId;
    }

    @Transactional
    public List<UnitResponseDto> getUnitList(Long courseId) {
        Course course = courseJpaRepository.findById(courseId)
                .orElseThrow(CCourseNotFoundException::new);

        List<Unit> unitList = course.getUnitList();

        if (unitList == null)
            return null;
        return unitList.stream().map(UnitResponseDto::new).collect(Collectors.toList());
    }
}
