package team7.simple.domain.auth.basic.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import team7.simple.domain.auth.basic.dto.LoginRequestDto;
import team7.simple.domain.auth.basic.dto.SignupRequestDto;
import team7.simple.domain.auth.basic.service.AuthService;
import team7.simple.domain.auth.jwt.dto.TokenRequestDto;
import team7.simple.domain.auth.jwt.dto.TokenResponseDto;
import team7.simple.global.common.response.dto.CommonResult;
import team7.simple.global.common.response.dto.SingleResult;
import team7.simple.global.common.response.service.ResponseService;

import javax.validation.Valid;


@Slf4j
@Api(tags = {"Basic Auth"})
@RequiredArgsConstructor
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
}
