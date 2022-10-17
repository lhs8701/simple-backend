package team7.simple.domain.course.controller;


import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import team7.simple.domain.course.dto.CourseRequestDto;
import team7.simple.domain.course.dto.CourseUpdateParam;
import team7.simple.domain.course.dto.RegisterCancelRequestDto;
import team7.simple.domain.course.service.CourseService;
import team7.simple.domain.user.entity.User;
import team7.simple.global.common.ConstValue;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @ApiOperation(value = "OPEN - 강좌 등록")
    @ApiImplicitParam(name = ConstValue.JWT_HEADER, value = "AccessToken", required = true, dataType = "String", paramType = "header")
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/open/course")
    public ResponseEntity<?> createCourse(@Valid CourseRequestDto courseRequestDto, @AuthenticationPrincipal User instructor) {
        return new ResponseEntity<>(courseService.createCourse(courseRequestDto, instructor), HttpStatus.OK);
    }

    @ApiOperation(value = "OPEN - 강좌 수강 신청")
    @ApiImplicitParam(name = ConstValue.JWT_HEADER, value = "AccessToken", required = true, dataType = "String", paramType = "header")
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/open/course/register")
    public ResponseEntity<?> register(@RequestBody RegisterCancelRequestDto registerCancelRequestDto, @AuthenticationPrincipal User user) {
        return new ResponseEntity<>(courseService.register(registerCancelRequestDto, user), HttpStatus.OK);
    }

    @ApiOperation(value = "OPEN - 강좌 수강 취소")
    @ApiImplicitParam(name = ConstValue.JWT_HEADER, value = "AccessToken", required = true, dataType = "String", paramType = "header")
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/open/course/cancel")
    public ResponseEntity<?> cancel(@RequestBody RegisterCancelRequestDto registerCancelRequestDto, @AuthenticationPrincipal User user) {
        courseService.cancel(registerCancelRequestDto, user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value = "OPEN - 강좌 정보 조회")
    @GetMapping("/open/course/{courseId}")
    public ResponseEntity<?> getCourseInfo(@PathVariable Long courseId) {
        return new ResponseEntity<>(courseService.getCourseInfo(courseId), HttpStatus.OK);
    }

    @ApiOperation(value = "OPEN - 강좌 정보 수정")
    @PatchMapping(value = "/open/course/{courseId}")
    public ResponseEntity<?> updateCourse(@PathVariable Long courseId, @RequestBody @Valid CourseUpdateParam courseUpdateParam) {
        return new ResponseEntity<>(courseService.updateCourse(courseId, courseUpdateParam), HttpStatus.OK);
    }

    @ApiOperation(value = "OPEN - 강좌 삭제")
    @DeleteMapping(value = "/open/course/{courseId}")
    public ResponseEntity<?> deleteCourse(@PathVariable Long courseId) {
        courseService.deleteCourse(courseId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
