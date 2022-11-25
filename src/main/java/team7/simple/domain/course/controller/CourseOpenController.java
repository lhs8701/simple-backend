package team7.simple.domain.course.controller;

import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import team7.simple.domain.course.dto.CourseRequestDto;
import team7.simple.domain.course.dto.CourseUpdateParam;
import team7.simple.domain.course.service.CourseService;
import team7.simple.domain.user.entity.User;
import team7.simple.global.common.constant.ConstValue;

import javax.validation.Valid;

@Api(tags = {"[Open API] Course"})
@RestController
@RequiredArgsConstructor
public class CourseOpenController {

    private final CourseService courseService;

    @ApiOperation(value = "OPEN - 강좌 등록",
            notes = """
                    강좌를 등록합니다.
                    \nparameter : 강좌 제목, 강좌 부제목
                    \nresponse : 등록된 강좌의 아이디
                    """
    )
    @ApiImplicitParam(name = ConstValue.JWT_HEADER, value = "AccessToken", required = true, dataType = "String", paramType = "header")
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/open/courses")
    public ResponseEntity<?> createCourse(@Valid CourseRequestDto courseRequestDto, @AuthenticationPrincipal User instructor) {
        return new ResponseEntity<>(courseService.createCourse(courseRequestDto, instructor), HttpStatus.OK);
    }


    @ApiOperation(value = "OPEN - 강좌 수강 신청",
            notes = """
                    강좌를 수강신청 합니다.
                    \nparameter : 수강 신청할 강좌의 아이디
                    \nresponse : X
                    """
    )
    @ApiImplicitParam(name = ConstValue.JWT_HEADER, value = "AccessToken", required = true, dataType = "String", paramType = "header")
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/open/courses/{courseId}/register")
    public ResponseEntity<?> register(@PathVariable Long courseId, @AuthenticationPrincipal User user) {
        courseService.register(courseId, user);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @ApiOperation(value = "OPEN - 강좌 수강 취소",
            notes = """
                    강좌를 수강취소 합니다.
                    \nparameter : 수강 취소할 강좌의 아이디
                    \nresponse : X
                    """
    )
    @ApiImplicitParam(name = ConstValue.JWT_HEADER, value = "AccessToken", required = true, dataType = "String", paramType = "header")
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/open/courses/{courseId}/cancel")
    public ResponseEntity<?> cancel(@PathVariable Long courseId, @AuthenticationPrincipal User user) {
        courseService.cancel(courseId, user);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @ApiOperation(value = "OPEN - 강좌 정보 조회",
            notes = """
                    강좌의 세부 정보를 조회합니다.
                    \nparameter : 조회할 강좌의 아이디
                    \nresponse : 강좌 아이디, 강좌 제목, 강좌 부제목, 강사 이름, 수강생 수
                    """
    )
    @GetMapping("/open/courses/{courseId}")
    public ResponseEntity<?> getCourseInfo(@PathVariable Long courseId) {
        return new ResponseEntity<>(courseService.getCourseInfo(courseId), HttpStatus.OK);
    }


    @ApiOperation(value = "OPEN - 강좌 정보 수정",
            notes = """
                    강좌의 세부 정보를 수정합니다.
                    \nparameter : 수정할 강좌의 아이디
                    \nresponse : 수정된 강좌의 아이디
                    """
    )
    @PatchMapping(value = "/open/courses/{courseId}")
    public ResponseEntity<?> updateCourse(@PathVariable Long courseId, @RequestBody @Valid CourseUpdateParam courseUpdateParam, @AuthenticationPrincipal User user) {
        return new ResponseEntity<>(courseService.updateCourse(courseId, courseUpdateParam, user), HttpStatus.OK);
    }


    @ApiOperation(value = "OPEN - 강좌 삭제",
            notes = """
                    강좌를 삭제합니다.
                    \nparameter : 삭제할 강좌의 아이디
                    \nresponse : X
                    """
    )
    @DeleteMapping(value = "/open/courses/{courseId}")
    public ResponseEntity<?> deleteCourse(@PathVariable Long courseId, @AuthenticationPrincipal User user) {
        courseService.deleteCourse(courseId, user);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
