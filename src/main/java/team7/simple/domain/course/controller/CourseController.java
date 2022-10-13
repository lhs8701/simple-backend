package team7.simple.domain.course.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team7.simple.domain.course.dto.CourseRequestDto;
import team7.simple.domain.course.dto.CourseUpdateParam;
import team7.simple.domain.course.service.CourseService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @PostMapping("/open/course")
    public ResponseEntity<?> uploadCourse(@Valid CourseRequestDto courseRequestDto) {
        return new ResponseEntity<>(courseService.createCourse(courseRequestDto), HttpStatus.OK);
    }

    @GetMapping("/open/course/{courseId}")
    public ResponseEntity<?> getCourse(@PathVariable Long courseId) {
        return new ResponseEntity<>(courseService.getCourse(courseId), HttpStatus.OK);
    }

    @PatchMapping(value = "/open/course/{courseId}")
    public ResponseEntity<?> updateCourse(@PathVariable Long courseId, @RequestBody @Valid CourseUpdateParam courseUpdateParam) {
        return new ResponseEntity<>(courseService.updateCourse(courseId, courseUpdateParam), HttpStatus.OK);
    }

    @DeleteMapping(value = "/open/course/{courseId}")
    public ResponseEntity<?> deleteCourse(@PathVariable Long courseId) {
        courseService.deleteCourse(courseId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
