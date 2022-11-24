package team7.simple.domain.user.controller;

import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import team7.simple.domain.user.dto.PasswordUpdateParam;
import team7.simple.domain.user.entity.User;
import team7.simple.domain.user.service.UserService;
import team7.simple.global.common.ConstValue;

@Api(tags = {"[Open API] User"})
@Controller
@RequiredArgsConstructor
@RequestMapping("/open/users")
public class UserOpenController {
    private final UserService userService;

    @ApiOperation(value = "OPEN - 회원 비밀번호 변경",
            notes = """
                    회원의 비밀번호를 변경합니다.
                    \nparameter : 변경할 비밀번호
                    \nresponse : X
                    """
    )
    @ApiImplicitParam(name = ConstValue.JWT_HEADER, value = "AccessToken", required = true, dataType = "String", paramType = "header")
    @PreAuthorize("isAuthenticated()")
    @PatchMapping("/password/change")
    public ResponseEntity<?> changePassword(@RequestBody PasswordUpdateParam passwordUpdateParam, @AuthenticationPrincipal User user) {
        userService.changePassword(passwordUpdateParam, user);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @ApiOperation(value = "OPEN - 등록한 강좌 목록 조회",
            notes = """
                    회원이 현재 수강중인 강좌의 목록을 반환합니다.
                    \nparameter : X
                    \nresponse : 강좌 아이디, 강좌 이름, 수강 등록 날짜
                    """
    )
    @ApiImplicitParam(name = ConstValue.JWT_HEADER, value = "AccessToken", required = true, dataType = "String", paramType = "header")
    @PreAuthorize("isAuthenticated()")
    @PatchMapping("/history/courses/")
    public ResponseEntity<?> getJoinedCourse(@AuthenticationPrincipal User user) {
        return new ResponseEntity<>(userService.getJoinedCourses(user), HttpStatus.OK);
    }


    @ApiOperation(value = "OPEN - 수강 현황 조회",
            notes = """
                    강좌에 대한 강의 수강 현황을 조회합니다.
                    \nparameter : 강좌 아이디
                    \nresponse : 강의 아이디, 제목, 완료 여부, 진척도
                    """
    )
    @ApiImplicitParam(name = ConstValue.JWT_HEADER, value = "AccessToken", required = true, dataType = "String", paramType = "header")
    @PreAuthorize("isAuthenticated()")
    @PatchMapping("/history/courses/{courseId}")
    public ResponseEntity<?> getStudyHistory(@PathVariable Long courseId, @AuthenticationPrincipal User user) {
        return new ResponseEntity<>(userService.getStudyHistory(courseId, user), HttpStatus.OK);
    }
}
