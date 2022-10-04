package team7.simple.domain.user.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import team7.simple.domain.user.dto.PasswordUpdateParam;
import team7.simple.domain.user.entity.User;
import team7.simple.domain.user.service.UserService;
import team7.simple.global.common.response.dto.CommonResult;
import team7.simple.global.common.response.service.ResponseService;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final ResponseService responseService;

    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "X-AUTH-TOKEN",
                    value = "AccessToken",
                    required = true, dataType = "String", paramType = "header"
            )
    })
    @ApiOperation(value = "회원 비밀번호 변경")
    @PreAuthorize("isAuthenticated()")
    @PatchMapping("/open/user/password")
    public CommonResult changePassword(@RequestBody PasswordUpdateParam passwordUpdateParam, @AuthenticationPrincipal User user){
        userService.changePassword(user.getUserId(), passwordUpdateParam);
        return responseService.getSuccessResult();
    }
}
