package team7.simple.domain.course.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import team7.simple.domain.course.service.CourseService;


@Api(tags = {"[Front API] Course"})
@RequiredArgsConstructor
@RequestMapping("/front/courses")
@Controller
public class CourseController {

    private final CourseService courseService;

    @ApiOperation(value = "FRONT - 강좌 정보 조회")
    @GetMapping("/{courseId}")
    public ResponseEntity<?> getCourseInfo(@PathVariable Long courseId) {
        return new ResponseEntity<>(courseService.getCourseInfo(courseId), HttpStatus.OK);
    }
}
