package team7.simple.domain.unit.controller;

import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import team7.simple.domain.record.dto.RatingRequestDto;
import team7.simple.domain.record.service.RatingService;
import team7.simple.domain.unit.dto.UnitPlayRequestDto;
import team7.simple.domain.unit.service.UnitService;
import team7.simple.domain.user.entity.User;
import team7.simple.global.common.ConstValue;

import javax.validation.Valid;

@Api(tags = {"[Front API] Unit"})
@Controller
@RequiredArgsConstructor
public class UnitController {
    private final UnitService unitService;
    private final RatingService ratingService;

    @ApiOperation(value = "FRONT - 강좌 내 강의 목록 조회")
    @ApiResponses(value = {
            @ApiResponse(code=200, message = "성공"),
            @ApiResponse(code=404, message = "해당 강좌가 없을 경우"),
    })
    @GetMapping("/front/course/{courseId}/unit")
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
    @PreAuthorize("permitAll()")
    @GetMapping("/front/course/unit/{unitId}/rating")
    public ResponseEntity<?> getRating(@PathVariable Long unitId) {

        return new ResponseEntity<>(ratingService.getAverageRatingScore(unitId), HttpStatus.OK);
    }
}
