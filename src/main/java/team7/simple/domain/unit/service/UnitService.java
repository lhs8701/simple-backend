package team7.simple.domain.unit.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import team7.simple.domain.course.entity.Course;
import team7.simple.domain.course.repository.CourseJpaRepository;
import team7.simple.domain.unit.dto.*;
import team7.simple.domain.unit.entity.Unit;
import team7.simple.domain.unit.repository.UnitJpaRepository;
import team7.simple.domain.user.entity.User;
import team7.simple.domain.video.dto.VideoDto;
import team7.simple.domain.video.entity.Video;
import team7.simple.domain.video.service.VideoService;
import team7.simple.domain.viewingrecord.entity.ViewingRecord;
import team7.simple.domain.viewingrecord.repository.ViewingRecordJpaRepository;
import team7.simple.global.error.advice.exception.CCourseNotFoundException;
import team7.simple.global.error.advice.exception.CUnitNotFoundException;
import team7.simple.infra.hls.service.HlsService;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UnitService {
    private final CourseJpaRepository courseJpaRepository;
    private final UnitJpaRepository unitJpaRepository;

    private final ViewingRecordJpaRepository viewingRecordJpaRepository;

    private final VideoService videoService;

    private final HlsService hlsService;

    @Transactional
    public Long createUnit(UnitRequestDto unitRequestDto, MultipartFile file) {
        Long courseId = unitRequestDto.getCourseId();
        Course course = courseJpaRepository.findById(courseId)
                .orElseThrow(CCourseNotFoundException::new);

        VideoDto videoDto = videoService.uploadVideo(file, courseId);
        String hlsFileUrl = hlsService.convertToM3u8(videoDto);

        Unit unit = unitRequestDto.toEntity(videoDto.toEntity(hlsFileUrl), course);

        return unitJpaRepository.save(unit).getUnitId();
    }

    /*임시*/
    @Transactional
    public Long createUnitLocal(UnitRequestDto unitRequestDto) {
        Long courseId = unitRequestDto.getCourseId();
        Course course = courseJpaRepository.findById(courseId)
                .orElseThrow(CCourseNotFoundException::new);

        Unit unit = unitRequestDto.toEntity(new Video("test", "test", "test"), course);

        return unitJpaRepository.save(unit).getUnitId();
    }

    /*임시*/
    @Transactional
    public Long createUnitByUrl(UnitRequestByUrlDto unitRequestByUrlDto) {
        Long courseId = unitRequestByUrlDto.getCourseId();
        Course course = courseJpaRepository.findById(courseId)
                .orElseThrow(CCourseNotFoundException::new);
        Video video = Video.builder()
                .fileName("test")
                .fileOriName("test")
                .fileUrl("test")
                .hlsFileUrl(unitRequestByUrlDto.getMediaUrl())
                .build();
        Unit unit = unitRequestByUrlDto.toEntity(video, course);

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

    @Transactional
    public UnitPlayResponseDto playUnit(Long unitId, UnitPlayRequestDto unitPlayRequestDto, User user) {
        double recordTime;

        if (unitPlayRequestDto.getCurrentUnitId() != -1) {
            unitJpaRepository.findById(unitPlayRequestDto.getCurrentUnitId()).orElseThrow(CUnitNotFoundException::new);
            saveCurrentViewingRecord(unitPlayRequestDto, user.getUserId());
        }
        Unit nextUnit = unitJpaRepository.findById(unitId).orElseThrow(CUnitNotFoundException::new);
        ViewingRecord nextUnitViewingRecord = viewingRecordJpaRepository.findByUnitId(nextUnit.getUnitId()).orElse(null);
        if (nextUnitViewingRecord == null) {
            recordTime = 0;
        } else {
            recordTime = nextUnitViewingRecord.getTime();
        }

        return UnitPlayResponseDto.builder()
                .unitId(nextUnit.getUnitId())
                .title(nextUnit.getTitle())
                .fileUrl(hlsService.getHlsFileUrl(nextUnit.getVideo()))
                .time(recordTime)
                .build();
    }

    private void saveCurrentViewingRecord(UnitPlayRequestDto unitPlayRequestDto,String userId) {
        ViewingRecord currentViewingRecord = viewingRecordJpaRepository
                .findByUnitAndUser(unitPlayRequestDto.getCurrentUnitId(), userId)
                .orElse(null);
        if (currentViewingRecord == null) {
            viewingRecordJpaRepository.save(ViewingRecord.builder()
                    .recordId(UUID.randomUUID().toString())
                    .unitId(unitPlayRequestDto.getCurrentUnitId())
                    .userId(userId)
                    .time(unitPlayRequestDto.getRecordTime())
                    .check(unitPlayRequestDto.isComplete())
                    .build());
        } else {
            currentViewingRecord.setTime(unitPlayRequestDto.getRecordTime());
            if (unitPlayRequestDto.isComplete()) {
                currentViewingRecord.setCheck(true);
            }
        }
    }

    @Transactional
    public List<UnitThumbnailResponseDto> getUnitThumbnailList(Long courseId) {
        Course course = courseJpaRepository.findById(courseId).orElseThrow(CCourseNotFoundException::new);
        List<Unit> unitList = course.getUnitList();
        if (unitList == null)
            return null;
        return unitList.stream().map(UnitThumbnailResponseDto::new).collect(Collectors.toList());
    }
}

