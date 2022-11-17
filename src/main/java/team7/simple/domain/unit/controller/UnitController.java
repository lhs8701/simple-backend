package team7.simple.domain.unit.controller;

import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import team7.simple.domain.answer.service.AnswerService;
import team7.simple.domain.question.service.QuestionService;
import team7.simple.domain.rating.dto.RatingRequestDto;
import team7.simple.domain.rating.service.RatingService;
import team7.simple.domain.unit.dto.UnitPlayRequestDto;
import team7.simple.domain.unit.dto.UnitRequestByUrlDto;
import team7.simple.domain.unit.dto.UnitRequestDto;
import team7.simple.domain.unit.dto.UnitUpdateParam;
import team7.simple.domain.unit.service.UnitService;
import team7.simple.domain.user.entity.User;
import team7.simple.global.common.ConstValue;

import javax.validation.Valid;

@Api(tags = {"Unit Controller"})
@RestController
@RequiredArgsConstructor
public class UnitController {
    private final UnitService unitService;
    private final RatingService ratingService;

    @ApiOperation(value = "OPEN - 강의 업로드", notes = "강의 원본 영상을 원격 서버에 업로드함과 동시에 m3u8 확장자로 변환합니다.")
    @ApiResponses(value = {
            @ApiResponse(code=200, message = "성공"),
            @ApiResponse(code=404, message = "해당 강좌가 없을 경우"),
    })
    @PostMapping("open/course/unit")
    public ResponseEntity<?> uploadUnit(@RequestPart @Valid UnitRequestDto unitRequestDto, @RequestPart MultipartFile file) {
        return new ResponseEntity<>(unitService.createUnit(unitRequestDto, file), HttpStatus.OK);
    }

    /*테스트용*/
    @ApiOperation(value = "OPEN - 강의 업로드 (로컬 테스트용)", notes = "로컬 환경에서 테스트 용으로 사용하는 API입니다.")
    @ApiResponses(value = {
            @ApiResponse(code=200, message = "성공"),
            @ApiResponse(code=404, message = "해당 강좌가 없을 경우"),
    })
    @PostMapping("open/course/unit/local")
    public ResponseEntity<?> uploadUnitLocal(@RequestBody @Valid UnitRequestDto unitRequestDto) {
        return new ResponseEntity<>(unitService.createUnitLocal(unitRequestDto), HttpStatus.OK);
    }

    /*테스트용*/
    @ApiOperation(value = "OPEN - 강의 업로드 (url로 접근, 테스트용) ", notes = "URL로 미디어 소스에 접근합니다. 테스트 용으로 사용하는 API입니다.")
    @ApiResponses(value = {
            @ApiResponse(code=200, message = "성공"),
            @ApiResponse(code=404, message = "해당 강좌가 없을 경우"),
    })
    @PostMapping("open/course/unit/url")
    public ResponseEntity<?> uploadUnitByUrl(@RequestBody @Valid UnitRequestByUrlDto unitRequestByUrlDto) {
        return new ResponseEntity<>(unitService.createUnitByUrl(unitRequestByUrlDto), HttpStatus.OK);
    }

    @ApiOperation(value = "OPEN - 강의 수정")
    @ApiResponses(value = {
            @ApiResponse(code=200, message = "성공"),
            @ApiResponse(code=404, message = "해당 강의가 없을 경우"),
    })
    @PatchMapping("/open/course/unit/{unitId}")
    public ResponseEntity<?> updateUnit(@RequestBody @Valid UnitUpdateParam unitUpdateParam) {
        return new ResponseEntity<>(unitService.updateUnit(unitUpdateParam), HttpStatus.OK);
    }

    @ApiOperation(value = "OPEN - 강의 삭제")
    @ApiResponses(value = {
            @ApiResponse(code=200, message = "성공"),
            @ApiResponse(code=404, message = "해당 강의가 없을 경우"),
    })
    @DeleteMapping("/open/course/unit/{unitId}")
    public ResponseEntity<?> deleteUnit(@PathVariable Long unitId) {
        unitService.deleteUnit(unitId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value = "FRONT - 강좌 내 강의 목록 조회")
    @ApiResponses(value = {
            @ApiResponse(code=200, message = "성공"),
            @ApiResponse(code=404, message = "해당 강좌가 없을 경우"),
    })
    @GetMapping("/open/course/{courseId}/unit")
    public ResponseEntity<?> getUnitThumbnailList(@PathVariable Long courseId) {
        return new ResponseEntity<>(unitService.getUnitThumbnailList(courseId), HttpStatus.OK);
    }

    @ApiOperation(value = "FRONT - 강의 재생", notes = "다른 강의로 이동할 경우 호출하는 API입니다. 현재 재생중인 강의의 시간대와 완료 여부를 기록한 후, 다음 강의 영상의 경로를 포함한 정보를 반환합니다.")
    @ApiResponses(value = {
            @ApiResponse(code=200, message = "성공"),
            @ApiResponse(code=404, message = "해당 강의가 없을 경우"),
    })
    @ApiImplicitParam(name = ConstValue.JWT_HEADER, value = "AccessToken", required = true, dataType = "String", paramType = "header")
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/front/course/unit/{unitId}")
    public ResponseEntity<?> playUnit(@PathVariable Long unitId, @RequestBody UnitPlayRequestDto unitPlayRequestDto, @AuthenticationPrincipal User user) {
        return new ResponseEntity<>(unitService.playUnit(unitId, unitPlayRequestDto, user), HttpStatus.OK);
    }

    @ApiOperation(value = "FRONT - 강의 평점 등록")
    @ApiResponses(value = {
            @ApiResponse(code=200, message = "성공"),
            @ApiResponse(code=403, message = "평점을 등록할 권한이 없는 경우"),
            @ApiResponse(code=404, message = "해당 강의를 찾을 수 없는 경우"),
    })
    @ApiImplicitParam(name = ConstValue.JWT_HEADER, value = "AccessToken", required = true, dataType = "String", paramType = "header")
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/front/course/unit/{unitId}/rating")
    public ResponseEntity<?> addRating(@PathVariable Long unitId, @RequestBody @Valid RatingRequestDto ratingRequestDto, @AuthenticationPrincipal User user) {
        ratingService.addRating(unitId, ratingRequestDto, user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value = "FRONT - 강좌 평점 조회")
    @ApiResponses(value = {
            @ApiResponse(code=200, message = "성공"),
            @ApiResponse(code=404, message = "등록된 평점이 없는 경우"),
    })
    @ApiImplicitParam(name = ConstValue.JWT_HEADER, value = "AccessToken", required = true, dataType = "String", paramType = "header")
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/front/course/unit/{unitId}/rating")
    public ResponseEntity<?> getRating(@PathVariable Long unitId) {

        return new ResponseEntity<>(ratingService.getAverageRatingScore(unitId), HttpStatus.OK);
    }
}
