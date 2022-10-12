package team7.simple.domain.course.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import team7.simple.domain.course.dto.CourseRequestDto;
import team7.simple.domain.course.dto.CourseResponseDto;
import team7.simple.domain.course.dto.CourseUpdateParam;
import team7.simple.domain.unit.dto.UnitRequestDto;
import team7.simple.domain.course.service.CourseService;
import team7.simple.domain.unit.dto.UnitResponseDto;
import team7.simple.domain.unit.dto.UnitUpdateParam;
import team7.simple.global.common.response.dto.CommonResult;
import team7.simple.global.common.response.dto.ListResult;
import team7.simple.global.common.response.dto.SingleResult;
import team7.simple.global.common.response.service.ResponseService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;
    private final ResponseService responseService;


    @GetMapping("/open/course/{courseId}")
    public SingleResult<CourseResponseDto> getCourse(@PathVariable Long courseId) {
        return responseService.getSingleResult(courseService.getCourse(courseId));
    }

    @PostMapping("/open/course")
    public SingleResult<Long> uploadCourse(@Valid CourseRequestDto courseRequestDto) {
        return responseService.getSingleResult(courseService.createCourse(courseRequestDto));
    }

    @DeleteMapping(value = "/open/course/{courseId}")
    public CommonResult deleteCourse(@PathVariable Long courseId) {
        courseService.deleteCourse(courseId);
        return responseService.getSuccessResult();
    }

    @PatchMapping(value = "/open/course/{courseId}")
    public SingleResult<Long> updateCourse(@PathVariable Long courseId, @RequestBody @Valid CourseUpdateParam courseUpdateParam) {
        return responseService.getSingleResult(courseService.updateCourse(courseId, courseUpdateParam));
    }


    @GetMapping("/open/course/{courseId}/unit")
    public ListResult<UnitResponseDto> getUnit(@PathVariable Long courseId) {
        return responseService.getListResult(courseService.getUnitList(courseId));
    }

    @PostMapping("open/course/{courseId}/unit")
    public SingleResult<Long> uploadUnit(@PathVariable Long courseId, @RequestPart @Valid UnitRequestDto unitRequestDto, @RequestPart MultipartFile file) {
        return responseService.getSingleResult(courseService.createUnit(courseId, unitRequestDto, file));
    }

    @DeleteMapping("/open/course/{courseId}/unit/{unitId}")
    public CommonResult deleteUnit(@PathVariable Long courseId, @PathVariable Long unitId) {
        courseService.deleteUnit(courseId, unitId);
        return responseService.getSuccessResult();
    }

    @PatchMapping("/open/course/{courseId}/unit/{unitId}")
    public SingleResult<Long> updateUnit(@PathVariable Long courseId, @RequestBody @Valid UnitUpdateParam unitUpdateParam) {
        return responseService.getSingleResult(courseService.updateUnit(courseId, unitUpdateParam));
    }

}
