package team7.simple.domain.unit.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import team7.simple.domain.course.entity.Course;
import team7.simple.domain.course.repository.CourseJpaRepository;
import team7.simple.domain.unit.dto.UnitRequestDto;
import team7.simple.domain.unit.dto.UnitUpdateParam;
import team7.simple.domain.unit.entity.Unit;
import team7.simple.domain.unit.repository.UnitJpaRepository;
import team7.simple.domain.video.dto.VideoDto;
import team7.simple.domain.video.entity.Video;
import team7.simple.domain.video.service.VideoService;
import team7.simple.global.error.advice.exception.CCourseNotFoundException;
import team7.simple.global.error.advice.exception.CUnitNotFoundException;

@RequiredArgsConstructor
@Service
public class UnitService {
    private final CourseJpaRepository courseJpaRepository;
    private final UnitJpaRepository unitJpaRepository;
    private final VideoService videoService;

    @Transactional
    public Long createUnit(UnitRequestDto unitRequestDto, MultipartFile file) {
        Long courseId = unitRequestDto.getCourseId();
        Course course = courseJpaRepository.findById(courseId)
                .orElseThrow(CCourseNotFoundException::new);

        VideoDto videoDto = videoService.uploadVideo(file, courseId);
        Unit unit = unitRequestDto.toEntity(videoDto.toEntity(), course);

        return unitJpaRepository.save(unit).getUnitId();
    }

    /*임시*/
    @Transactional
    public Long createUnit(UnitRequestDto unitRequestDto) {
        Long courseId = unitRequestDto.getCourseId();
        Course course = courseJpaRepository.findById(courseId)
                .orElseThrow(CCourseNotFoundException::new);

        Unit unit = unitRequestDto.toEntity(new Video("test", "test", "test"), course);

        return unitJpaRepository.save(unit).getUnitId();
    }

    @Transactional
    public void deleteUnit(Long unitId) {
        Unit unit = unitJpaRepository.findById(unitId).orElseThrow(CUnitNotFoundException::new);
        unitJpaRepository.delete(unit);
    }

    @Transactional
    public Long updateUnit(UnitUpdateParam unitUpdateParam) {
        Long unitId = unitUpdateParam.getUnitId();
        Unit unit = unitJpaRepository.findById(unitId)
                .orElseThrow(CUnitNotFoundException::new);

        unit.setTitle(unitUpdateParam.getTitle());

        return unitId;
    }
}
