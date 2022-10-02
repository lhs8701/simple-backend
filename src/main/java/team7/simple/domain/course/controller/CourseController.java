package team7.simple.domain.course.controller;


import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import team7.simple.domain.course.dto.CourseResponseDto;
import team7.simple.domain.course.service.CourseService;
import team7.simple.global.common.response.dto.ListResult;
import team7.simple.global.common.response.dto.SingleResult;
import team7.simple.global.common.response.service.ResponseService;

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


}
