package team7.simple.domain.auth.basic.controller;


import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import team7.simple.domain.auth.basic.dto.LoginRequestDto;
import team7.simple.domain.auth.basic.dto.SignupRequestDto;
import team7.simple.domain.auth.basic.service.AuthService;
import team7.simple.domain.auth.jwt.dto.TokenRequestDto;
import team7.simple.domain.auth.jwt.dto.TokenResponseDto;
import team7.simple.domain.user.entity.User;
import team7.simple.global.common.response.dto.CommonResult;
import team7.simple.global.common.response.dto.SingleResult;
import team7.simple.global.common.response.service.ResponseService;

import javax.validation.Valid;

@Slf4j
@Api(tags = {"Basic Auth"})
@RequiredArgsConstructor
@RequestMapping("/open/auth")
@RestController
public class AuthController {

    private final AuthService authService;
    private final ResponseService responseService;

    @ApiOperation(value="회원가입")
    @PreAuthorize("permitAll()")
    @PostMapping("/signup")
    public CommonResult signup(@RequestBody SignupRequestDto signupRequestDto){
        authService.signup(signupRequestDto);
        return responseService.getSuccessResult();
    }

    @ApiOperation(value="로그인")
    @PreAuthorize("permitAll()")
    @PostMapping("/login")
    public SingleResult<TokenResponseDto> login(@RequestBody LoginRequestDto loginRequestDto){
        return responseService.getSingleResult(authService.login(loginRequestDto));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "X-AUTH-TOKEN",
                    value = "AccessToken",
                    required = true, dataType = "String", paramType = "header"
            )
    })
    @PreAuthorize("isAuthenticated()")
    @PostMapping(value = "/logout", headers = "X-AUTH-TOKEN")
    public CommonResult logout(@RequestHeader("X-AUTH-TOKEN") String accessToken, @AuthenticationPrincipal User user) {
        authService.logout(accessToken, user);
        return responseService.getSuccessResult();
    }

    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "X-AUTH-TOKEN",
                    value = "AccessToken",
                    required = true, dataType = "String", paramType = "header"
            )
    })
    @PreAuthorize("isAuthenticated()")
    @PostMapping(value = "/withdrawal", headers = "X-AUTH-TOKEN")
    public CommonResult withdrawal(@RequestHeader("X-AUTH-TOKEN") String accessToken, @AuthenticationPrincipal User user) {
        authService.withdrawal(accessToken, user);
        return responseService.getSuccessResult();
    }

    @ApiOperation(value = "액세스, 리프레시 토큰 재발급")
    @PreAuthorize("permitAll()")
    @PostMapping(value = "/reissue")
    public SingleResult<TokenResponseDto> reissue(@RequestBody @Valid TokenRequestDto tokenRequestDto) {
        return responseService.getSingleResult(authService.reissue(tokenRequestDto));
    }
}
