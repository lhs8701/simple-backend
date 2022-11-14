package team7.simple.domain.unit.service;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;
import team7.simple.domain.course.entity.Course;
import team7.simple.domain.course.repository.CourseJpaRepository;
import team7.simple.domain.rating.dto.RatingRequestDto;
import team7.simple.domain.unit.dto.*;
import team7.simple.domain.unit.entity.Unit;
import team7.simple.domain.unit.repository.UnitJpaRepository;
import team7.simple.domain.user.entity.User;
import team7.simple.domain.video.dto.VideoDto;
import team7.simple.domain.video.entity.Video;
import team7.simple.domain.video.service.VideoService;
import team7.simple.domain.viewingrecord.entity.ViewingRecord;
import team7.simple.domain.viewingrecord.repository.ViewingRecordRedisRepository;
import team7.simple.global.common.ConstValue;
import team7.simple.global.error.advice.exception.CAccessDeniedException;
import team7.simple.global.error.advice.exception.CCourseNotFoundException;
import team7.simple.global.error.advice.exception.CUnitNotFoundException;
import team7.simple.infra.hls.service.HlsService;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UnitService {
    private final CourseJpaRepository courseJpaRepository;
    private final UnitJpaRepository unitJpaRepository;

    private final ViewingRecordRedisRepository viewingRecordRedisRepository;

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
        boolean complete = unitPlayRequestDto.isComplete();

        if (unitPlayRequestDto.getCurrentUnitId() != -1) {
            Unit currentUnit = unitJpaRepository.findById(unitPlayRequestDto.getCurrentUnitId()).orElseThrow(CUnitNotFoundException::new);
            saveCurrentViewingRecord(unitPlayRequestDto, currentUnit.getUnitId(), user.getUserId(), complete);
        }
        Unit nextUnit = unitJpaRepository.findById(unitId).orElseThrow(CUnitNotFoundException::new);
        ViewingRecord nextUnitViewingRecord = viewingRecordRedisRepository.findByUnitId(nextUnit.getUnitId()).orElse(null);
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
    private void saveCurrentViewingRecord(UnitPlayRequestDto unitPlayRequestDto, Long unitId, String userId, boolean complete) {
        ViewingRecord currentViewingRecord = viewingRecordRedisRepository.findByUnitIdAndUserId(unitId, userId).orElse(null);
        if (currentViewingRecord == null) {
            viewingRecordRedisRepository.save(ViewingRecord.builder()
                    .recordId(UUID.randomUUID().toString())
                    .unitId(unitId)
                    .userId(userId)
                    .time(unitPlayRequestDto.getRecordTime())
                    .check(complete) //complete
                    .build());
        }
        else{
            currentViewingRecord.setTime(unitPlayRequestDto.getRecordTime());
            currentViewingRecord.setCheck(complete);
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

