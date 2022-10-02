package team7.simple.domain.course.controller;


import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import team7.simple.domain.course.dto.CourseRequestDto;
import team7.simple.domain.course.dto.CourseResponseDto;
import team7.simple.domain.course.dto.CourseUpdateParam;
import team7.simple.domain.course.service.CourseService;
import team7.simple.global.common.response.dto.CommonResult;
import team7.simple.global.common.response.dto.ListResult;
import team7.simple.global.common.response.dto.SingleResult;
import team7.simple.global.common.response.service.ResponseService;

import javax.validation.Valid;

@RestController
@RequestMapping("/open/course")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;
    private final ResponseService responseService;


    @GetMapping("/{courseId}")
    public SingleResult<CourseResponseDto> getCourse(@PathVariable Long courseId) {
        return responseService.getSingleResult(courseService.getCourse(courseId));
    }

    @PostMapping("")
    public SingleResult<Long> upload(@RequestPart @Valid CourseRequestDto courseRequestDto) {
        return responseService.getSingleResult(courseService.create(courseRequestDto));
    }

    @DeleteMapping(value = "/{courseId}")
    public CommonResult delete(@PathVariable Long courseId) {
        courseService.deleteCourse(courseId);
        return responseService.getSuccessResult();
    }

    @PostMapping(value = "/{courseId}")
    public SingleResult<Long> update(@PathVariable Long courseId, @RequestBody @Valid CourseUpdateParam courseUpdateParam) {
        return responseService.getSingleResult(courseService.update(courseId, courseUpdateParam));
    }


}
