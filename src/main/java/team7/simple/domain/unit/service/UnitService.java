package team7.simple.domain.unit.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import team7.simple.domain.course.service.CourseFindService;
import team7.simple.domain.record.entity.Record;
import team7.simple.domain.record.repository.RecordJpaRepository;
import team7.simple.domain.record.service.RecordFindService;
import team7.simple.domain.record.service.RecordService;
import team7.simple.global.error.advice.exception.CAccessDeniedException;
import team7.simple.domain.course.entity.Course;
import team7.simple.domain.course.service.CourseService;
import team7.simple.domain.file.entity.Video;
import team7.simple.domain.rating.dto.RatingDetailResponseDto;
import team7.simple.domain.rating.repository.RatingJpaRepository;
import team7.simple.domain.rating.service.RatingService;
import team7.simple.domain.unit.dto.*;
import team7.simple.domain.unit.entity.Unit;
import team7.simple.domain.unit.repository.UnitJpaRepository;
import team7.simple.domain.file.dto.VideoDto;
import team7.simple.domain.file.service.VideoService;
import team7.simple.domain.unit.error.exception.CUnitNotFoundException;
import team7.simple.domain.user.entity.User;
import team7.simple.infra.hls.service.HlsService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j
public class UnitService {
    private final UnitJpaRepository unitJpaRepository;
    private final CourseFindService courseFindService;
    private final VideoService videoService;
    private final HlsService hlsService;
    private final RatingJpaRepository ratingJpaRepository;
    private final RatingService ratingService;
    private final RecordFindService recordFindService;

    private final UnitFindService unitFindService;

    @Transactional
    public Long createUnit(Long courseId, UnitRequestDto unitRequestDto, MultipartFile file) {
        Course course = courseFindService.getCourseById(courseId);

        VideoDto videoDto = videoService.uploadVideo(file, courseId);
        String hlsFileUrl = hlsService.convertToM3u8(videoDto);

        Unit unit = unitRequestDto.toEntity(videoDto.toEntity(hlsFileUrl), course);

        return unitJpaRepository.save(unit).getId();
    }

    /*임시*/
    @Transactional
    public Long createUnitLocal(Long courseId, UnitRequestDto unitRequestDto) {
        Course course = courseFindService.getCourseById(courseId);
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
        Unit unit = unitFindService.getUnitById(unitId);
        if (!unit.getCourse().getInstructor().getAccount().equals(user.getAccount())) {
            throw new CAccessDeniedException();
        }
        unit.update(unitUpdateParam.getTitle(), unitUpdateParam.getDescription(), unitUpdateParam.getObjective());

        return unitId;
    }

    public List<UnitThumbnailResponseDto> getUnits(Long courseId) {
        Course course = courseFindService.getCourseById(courseId);
        List<Unit> unitList = course.getUnitList();
        if (unitList == null)
            return null;

        return unitList.stream().map(UnitThumbnailResponseDto::new).collect(Collectors.toList());
    }

    /**
     * 사용자의 시청 완료 여부도 유닛 정보에 담아 함께 반환합니다.
     * @param courseId 강좌아이디
     * @param user 사용자
     * @return
     */
    public List<UnitThumbnailResponseDto> getUnits(Long courseId, User user) {
        List<UnitThumbnailResponseDto> returnList = new ArrayList<>();
        Course course = courseFindService.getCourseById(courseId);
        List<Unit> unitList = course.getUnitList();
        if (unitList == null)
            return null;

        for (Unit unit : unitList) {
            boolean completed = true;
            Record record = recordFindService.getRecordByUnitAndUser(unit, user).orElse(null);
            if (record == null || !record.isCompleted()) {
                completed = false;
            }
            returnList.add(new UnitThumbnailResponseDto(unit, completed));
        }
        return returnList;
    }

    /**
     * 강의의 세부 정보를 반환합니다.
     *
     * @param unitId 유닛아이디
     * @return UnitDetailResponseDto (유닛아이디, 제목, 강의 소개, 강의 목표)
     */
    public UnitResponseDto getUnitInfo(Long unitId) {
        Unit unit = unitFindService.getUnitById(unitId);
        return new UnitResponseDto(unit);
    }


    /**
     * 강의 세부 정보를 조회합니다.
     * @param unitId 유닛아이디
     * @return UnitDetailResponseDto (유닛아이디, 제목, 강의 소개, 강의 목표, 강의 평균 평점, 세부 평점 목록)
     */
    public UnitDetailResponseDto getUnitDetail(Long unitId) {
        Unit unit = unitFindService.getUnitById(unitId);
        double averageScore = ratingService.getAverageRatingScore(unitId).getScore();
        List<RatingDetailResponseDto> ratingList = ratingJpaRepository.findAllByUnit(unit).stream().map(RatingDetailResponseDto::new).toList();
        return new UnitDetailResponseDto(unit, averageScore, ratingList);
    }
}


