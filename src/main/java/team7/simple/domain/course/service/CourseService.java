package team7.simple.domain.course.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import team7.simple.domain.course.dto.CourseRequestDto;
import team7.simple.domain.course.dto.CourseResponseDto;
import team7.simple.domain.course.dto.CourseUpdateParam;
import team7.simple.domain.unit.dto.UnitRequestDto;
import team7.simple.domain.course.entity.Course;
import team7.simple.domain.course.repository.CourseJpaRepository;
import team7.simple.domain.unit.dto.UnitResponseDto;
import team7.simple.domain.unit.entity.Unit;
import team7.simple.domain.unit.repository.UnitJpaRepository;
import team7.simple.domain.unit.service.UnitService;
import team7.simple.domain.video.dto.VideoDto;
import team7.simple.domain.video.entity.Video;
import team7.simple.domain.video.service.VideoService;
import team7.simple.global.error.advice.exception.CCourseNotFoundException;

import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class CourseService {

    private final CourseJpaRepository courseJpaRepository;
    private final UnitJpaRepository unitJpaRepository;

    private final UnitService unitService;
    private final VideoService videoService;

    @Transactional
    public CourseResponseDto getCourse(Long courseId) {
        Course course = courseJpaRepository.findById(courseId)
                .orElseThrow(CCourseNotFoundException::new);
        return new CourseResponseDto(course);
    }

    @Transactional
    public Long createCourse(CourseRequestDto courseRequestDto) {
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
    public Long updateCourse(Long courseId, CourseUpdateParam courseUpdateParam) {
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

    @Transactional
    public Long createUnit(Long courseId, UnitRequestDto unitRequestDto, MultipartFile file) {
        Course course = courseJpaRepository.findById(courseId)
                .orElseThrow(CCourseNotFoundException::new);

        VideoDto videoDto = videoService.uploadVideo(file, courseId);
        Unit unit = unitRequestDto.toEntity(videoDto.toEntity());

        // course 안에 unitList에 얘를 어캐 추가하지,,

        return unitJpaRepository.save(unit).getUnitId();
    }

    @Transactional
    public void deleteUnit(Long courseId, Long unitId) {
        Course course = courseJpaRepository.findById(courseId)
                .orElseThrow(CCourseNotFoundException::new);

        // Course 안에 unitList에 있는 unit 가져오기
        // unitService.deleteUnit(unit.getFileUrl());
    }



}
