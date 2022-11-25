package team7.simple.domain.unit.controller;

import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import team7.simple.domain.rating.dto.RatingRequestDto;
import team7.simple.domain.rating.service.RatingService;
import team7.simple.domain.unit.service.UnitService;
import team7.simple.domain.user.entity.User;
import team7.simple.global.common.constant.ConstValue;

import javax.validation.Valid;

@Api(tags = {"[Front API] Unit"})
@Controller
@RequiredArgsConstructor
public class UnitController {
    private final UnitService unitService;
    private final RatingService ratingService;

    @ApiOperation(value = "FRONT - 강좌 내 강의 목록 조회")
    @GetMapping("/front/courses/{courseId}/units")
    public ResponseEntity<?> getUnits(@PathVariable Long courseId) {
        return new ResponseEntity<>(unitService.getUnits(courseId), HttpStatus.OK);
    }

    @ApiOperation(value = "FRONT - 강의 평점 등록")
    @ApiImplicitParam(name = ConstValue.JWT_HEADER, value = "AccessToken", required = true, dataType = "String", paramType = "header")
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/front/units/{unitId}/rating")
    public ResponseEntity<?> addRating(@PathVariable Long unitId, @RequestBody @Valid RatingRequestDto ratingRequestDto, @AuthenticationPrincipal User user) {
        ratingService.addRating(unitId, ratingRequestDto, user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value = "FRONT - 강좌 평점 조회")
    @PreAuthorize("permitAll()")
    @GetMapping("/front/units/{unitId}/rating")
    public ResponseEntity<?> getRating(@PathVariable Long unitId) {

        return new ResponseEntity<>(ratingService.getAverageRatingScore(unitId), HttpStatus.OK);
    }
}
