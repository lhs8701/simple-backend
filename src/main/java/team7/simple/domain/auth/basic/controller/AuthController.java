package team7.simple.domain.auth.basic.controller;


import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import team7.simple.domain.auth.basic.dto.LoginRequestDto;
import team7.simple.domain.auth.basic.dto.SignupRequestDto;
import team7.simple.domain.auth.basic.service.AuthService;
import team7.simple.domain.auth.jwt.dto.TokenRequestDto;
import team7.simple.domain.auth.jwt.dto.TokenResponseDto;
import team7.simple.domain.user.entity.User;
import team7.simple.global.common.ConstValue;
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

    @ApiOperation(value = "OPEN - 회원가입")
    @PreAuthorize("permitAll()")
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupRequestDto signupRequestDto) {
        authService.signup(signupRequestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value = "OPEN - 로그인")
    @PreAuthorize("permitAll()")
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto loginRequestDto) {
        return new ResponseEntity<>(authService.login(loginRequestDto), HttpStatus.OK);
    }

    @ApiOperation(value = "OPEN - 로그아웃")
    @ApiImplicitParam(name = ConstValue.JWT_HEADER, value = "AccessToken", required = true, dataType = "String", paramType = "header")
    @PreAuthorize("isAuthenticated()")
    @PostMapping(value = "/logout")
    public ResponseEntity<?> logout(@RequestHeader(ConstValue.JWT_HEADER) String accessToken, @AuthenticationPrincipal User user) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value = "OPEN - 회원 탈퇴")
    @ApiImplicitParam(name = ConstValue.JWT_HEADER, value = "AccessToken", required = true, dataType = "String", paramType = "header")
    @PreAuthorize("isAuthenticated()")
    @PostMapping(value = "/withdrawal")
    public ResponseEntity<?> withdrawal(@RequestHeader(ConstValue.JWT_HEADER) String accessToken, @AuthenticationPrincipal User user) {
        authService.withdrawal(accessToken, user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value = "OPEN - 액세스, 리프레시 토큰 재발급")
    @PreAuthorize("permitAll()")
    @PostMapping(value = "/reissue")
    public ResponseEntity<?> reissue(@RequestBody @Valid TokenRequestDto tokenRequestDto) {
        return new ResponseEntity<>(authService.reissue(tokenRequestDto), HttpStatus.OK);
    }
}
