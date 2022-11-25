package team7.simple.domain.unit.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import team7.simple.domain.auth.error.exception.CAccessDeniedException;
import team7.simple.domain.course.entity.Course;
import team7.simple.domain.course.service.CourseService;
import team7.simple.domain.file.entity.Video;
import team7.simple.domain.record.service.RecordService;
import team7.simple.domain.unit.dto.*;
import team7.simple.domain.unit.entity.Unit;
import team7.simple.domain.unit.repository.UnitJpaRepository;
import team7.simple.domain.file.dto.VideoDto;
import team7.simple.domain.file.service.VideoService;
import team7.simple.domain.unit.error.exception.CUnitNotFoundException;
import team7.simple.domain.user.entity.User;
import team7.simple.infra.hls.service.HlsService;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j
public class UnitService {
    private final UnitJpaRepository unitJpaRepository;
    private final CourseService courseService;
    private final VideoService videoService;
    private final HlsService hlsService;

    @Transactional
    public Long createUnit(Long courseId, UnitRequestDto unitRequestDto, MultipartFile file) {
        Course course = courseService.getCourseById(courseId);

        VideoDto videoDto = videoService.uploadVideo(file, courseId);
        String hlsFileUrl = hlsService.convertToM3u8(videoDto);

        Unit unit = unitRequestDto.toEntity(videoDto.toEntity(hlsFileUrl), course);

        return unitJpaRepository.save(unit).getId();
    }

    /*임시*/
    @Transactional
    public Long createUnitLocal(Long courseId, UnitRequestDto unitRequestDto) {
        Course course = courseService.getCourseById(courseId);
        Video video = new Video("test", "test", "test");
        Unit unit = unitRequestDto.toEntity(video, course);
        return unitJpaRepository.save(unit).getId();
    }

    @Transactional
    public void deleteUnit(Long unitId, User user) {
        Unit unit = unitJpaRepository.findById(unitId).orElseThrow(CUnitNotFoundException::new);
        if (!unit.getCourse().getInstructor().getAccount().equals(user.getAccount())) {
            throw new CAccessDeniedException();
        }
        unitJpaRepository.delete(unit);
    }

    @Transactional
    public Long updateUnit(Long unitId, UnitUpdateParam unitUpdateParam, User user) {
        Unit unit = getUnitById(unitId);
        if (!unit.getCourse().getInstructor().getAccount().equals(user.getAccount())) {
            throw new CAccessDeniedException();
        }
        unit.update(unitUpdateParam.getTitle(), unitUpdateParam.getDescription(), unitUpdateParam.getObjective());

        return unitId;
    }

    @Transactional
    public Unit getUnitById(Long unitId) {
        return unitJpaRepository.findById(unitId).orElseThrow(CUnitNotFoundException::new);
    }

    public List<UnitThumbnailResponseDto> getUnits(Long courseId) {
        Course course = courseService.getCourseById(courseId);
        List<Unit> unitList = course.getUnitList();
        if (unitList == null)
            return null;
        return unitList.stream().map(UnitThumbnailResponseDto::new).collect(Collectors.toList());
    }

    /**
     * 강의의 세부 정보를 반환합니다.
     *
     * @param unitId 유닛아이디
     * @return UnitDetailResponseDto (유닛아이디, 제목, 강의 소개, 강의 목표)
     */
    public UnitDetailResponseDto getUnitInfo(Long unitId) {
        Unit unit = getUnitById(unitId);
        return new UnitDetailResponseDto(unit);
    }
}


