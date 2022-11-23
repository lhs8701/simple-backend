package team7.simple.openapi;

import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import team7.simple.domain.auth.dto.LoginRequestDto;
import team7.simple.domain.auth.dto.SignupRequestDto;
import team7.simple.domain.auth.jwt.dto.TokenRequestDto;
import team7.simple.domain.auth.service.AuthService;
import team7.simple.domain.user.entity.User;
import team7.simple.global.common.ConstValue;

import javax.validation.Valid;

@Api(tags = {"[Open API] Authentication"})
@RequiredArgsConstructor
@RequestMapping("/open/auth")
@Controller
public class AuthOpenController {

    private final AuthService authService;

    @ApiOperation(value = "OPEN - 회원가입")
    @ApiResponses(value = {
            @ApiResponse(code=200, message = "성공"),
            @ApiResponse(code=409, message = "이미 가입된 계정인 경우"),
    })
    @PreAuthorize("permitAll()")
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupRequestDto signupRequestDto) {
        authService.signup(signupRequestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value = "OPEN - 로그인")
    @ApiResponses(value = {
            @ApiResponse(code=200, message = "성공"),
            @ApiResponse(code=400, message = "패스워드가 다를 경우"),
    })
    @PreAuthorize("permitAll()")
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto loginRequestDto) {
        return new ResponseEntity<>(authService.login(loginRequestDto), HttpStatus.OK);
    }

    @ApiOperation(value = "OPEN - 로그아웃")
    @ApiResponses(value = {
            @ApiResponse(code=200, message = "성공"),
            @ApiResponse(code=401, message = "토큰이 유효하지 않거나, 만료된 경우"),
            @ApiResponse(code=403, message = "접근 권한이 없을 경우"),
    })
    @ApiImplicitParam(name = ConstValue.JWT_HEADER, value = "AccessToken", required = true, dataType = "String", paramType = "header")
    @PreAuthorize("isAuthenticated()")
    @PostMapping(value = "/logout")
    public ResponseEntity<?> logout(@RequestHeader(ConstValue.JWT_HEADER) String accessToken, @AuthenticationPrincipal User user) {
        authService.logout(accessToken, user);
        return new ResponseEntity<>(HttpStatus.OK);

    }

    @ApiOperation(value = "OPEN - 회원 탈퇴")
    @ApiResponses(value = {
            @ApiResponse(code=200, message = "성공"),
            @ApiResponse(code=401, message = "토큰이 유효하지 않거나, 만료된 경우"),
            @ApiResponse(code=403, message = "접근 권한이 없을 경우"),
    })
    @ApiImplicitParam(name = ConstValue.JWT_HEADER, value = "AccessToken", required = true, dataType = "String", paramType = "header")
    @PreAuthorize("isAuthenticated()")
    @PostMapping(value = "/withdrawal")
    public ResponseEntity<?> withdrawal(@RequestHeader(ConstValue.JWT_HEADER) String accessToken, @AuthenticationPrincipal User user) {
        authService.withdrawal(accessToken, user);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @ApiOperation(value = "OPEN - 액세스, 리프레시 토큰 재발급")
    @ApiResponses(value = {
            @ApiResponse(code=200, message = "성공"),
            @ApiResponse(code=401, message = "토큰이 유효하지 않은 경우"),
            @ApiResponse(code=403, message = "접근 권한이 없을 경우"),
    })
    @PreAuthorize("permitAll()")
    @PostMapping(value = "/reissue")
    public ResponseEntity<?> reissue(@RequestBody @Valid TokenRequestDto tokenRequestDto) {
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
