package team7.simple.domain.course.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import team7.simple.domain.course.dto.CourseRequestDto;
import team7.simple.domain.course.dto.CourseResponseDto;
import team7.simple.domain.course.dto.CourseUpdateParam;
import team7.simple.domain.course.service.CourseService;
import team7.simple.domain.unit.dto.UnitResponseDto;
import team7.simple.domain.unit.entity.Unit;
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
    public SingleResult<Long> upload(@Valid CourseRequestDto courseRequestDto) {
        return responseService.getSingleResult(courseService.create(courseRequestDto));
    }

    @DeleteMapping(value = "/open/course/{courseId}")
    public CommonResult delete(@PathVariable Long courseId) {
        courseService.deleteCourse(courseId);
        return responseService.getSuccessResult();
    }

    @PatchMapping(value = "/open/course/{courseId}")
    public SingleResult<Long> update(@PathVariable Long courseId, @RequestBody @Valid CourseUpdateParam courseUpdateParam) {
        return responseService.getSingleResult(courseService.update(courseId, courseUpdateParam));
    }

    @GetMapping("/open/course/{courseId}/unit")
    public ListResult<UnitResponseDto> getUnit(@PathVariable Long courseId) {
        return responseService.getListResult(courseService.getUnitList(courseId));
    }


}
