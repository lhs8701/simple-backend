package team7.simple.domain.unit.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import team7.simple.domain.course.entity.Course;
import team7.simple.domain.course.service.CourseService;
import team7.simple.domain.record.entity.Record;
import team7.simple.domain.record.service.RecordService;
import team7.simple.domain.unit.dto.*;
import team7.simple.domain.unit.entity.Unit;
import team7.simple.domain.unit.repository.UnitJpaRepository;
import team7.simple.domain.user.entity.User;
import team7.simple.domain.file.dto.VideoDto;
import team7.simple.domain.file.entity.Video;
import team7.simple.domain.file.service.VideoService;
import team7.simple.domain.unit.error.exception.CUnitNotFoundException;
import team7.simple.infra.hls.service.HlsService;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UnitService {
    private final UnitJpaRepository unitJpaRepository;
    private final CourseService courseService;
    private final RecordService recordService;
    private final VideoService videoService;
    private final HlsService hlsService;

    @Transactional
    public Long createUnit(UnitRequestDto unitRequestDto, MultipartFile file) {
        Long courseId = unitRequestDto.getCourseId();
        Course course = courseService.getCourseById(courseId);

        VideoDto videoDto = videoService.uploadVideo(file, courseId);
        String hlsFileUrl = hlsService.convertToM3u8(videoDto);

        Unit unit = unitRequestDto.toEntity(videoDto.toEntity(hlsFileUrl), course);

        return unitJpaRepository.save(unit).getId();
    }

    /*임시*/
    @Transactional
    public Long createUnitLocal(UnitRequestDto unitRequestDto) {
        Long courseId = unitRequestDto.getCourseId();
        Course course = courseService.getCourseById(courseId);
        Unit unit = unitRequestDto.toEntity(new Video("test", "test", "test"), course);

        return unitJpaRepository.save(unit).getId();
    }

    /*임시*/
    @Transactional
    public Long createUnitByUrl(UnitRequestByUrlDto unitRequestByUrlDto) {
        Long courseId = unitRequestByUrlDto.getCourseId();
        Course course = courseService.getCourseById(courseId);
        Video video = Video.builder()
                .fileName("test")
                .fileOriName("test")
                .fileUrl("test")
                .hlsFileUrl(unitRequestByUrlDto.getMediaUrl())
                .build();
        Unit unit = unitRequestByUrlDto.toEntity(video, course);

        return unitJpaRepository.save(unit).getId();
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

    @Transactional
    public UnitPlayResponseDto playUnit(Long unitId, UnitPlayRequestDto unitPlayRequestDto, User user) {

        if (unitPlayRequestDto.getCurrentUnitId() != -1) {
            changeCurrentUnitRecord(unitPlayRequestDto, user);
        }
        Unit nextUnit = unitJpaRepository.findById(unitId).orElseThrow(CUnitNotFoundException::new);

        return UnitPlayResponseDto.builder()
                .unitId(nextUnit.getId())
                .title(nextUnit.getTitle())
                .fileUrl(hlsService.getHlsFileUrl(nextUnit.getVideo()))
                .time(recordService.getTimeline(user, nextUnit))
                .build();
    }

    private void changeCurrentUnitRecord(UnitPlayRequestDto unitPlayRequestDto, User user) {
        Unit unit = unitJpaRepository.findById(unitPlayRequestDto.getCurrentUnitId()).orElseThrow(CUnitNotFoundException::new);
        Record record = recordService.getRecordByUnitAndUser(unit, user).orElse(null);

        if (record == null) {
            recordService.saveRecord(unit, user, unitPlayRequestDto.getRecordTime(), unitPlayRequestDto.isComplete());
            return;
        }
        renewExistingRecordInfo(unitPlayRequestDto, record);
    }

    private void renewExistingRecordInfo(UnitPlayRequestDto unitPlayRequestDto, Record record) {
        record.setTimeline(unitPlayRequestDto.getRecordTime());
        if (unitPlayRequestDto.isComplete() && !record.isCompleted()) {
            record.setCompleted(true);
        }
    }

    @Transactional
    public List<UnitThumbnailResponseDto> getUnitThumbnailList(Long courseId) {
        Course course = courseService.getCourseById(courseId);
        List<Unit> unitList = course.getUnitList();
        if (unitList == null)
            return null;
        return unitList.stream().map(UnitThumbnailResponseDto::new).collect(Collectors.toList());
    }

    @Transactional
    public Unit findUnitById(Long unitId){
        return unitJpaRepository.findById(unitId).orElseThrow(CUnitNotFoundException::new);
    }
}

