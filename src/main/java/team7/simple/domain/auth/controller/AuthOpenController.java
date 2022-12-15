package team7.simple.domain.auth.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import team7.simple.domain.auth.dto.LoginRequestDto;
import team7.simple.domain.auth.dto.SignupRequestDto;
import team7.simple.domain.auth.jwt.dto.TokenRequestDto;
import team7.simple.domain.auth.service.AuthService;
import team7.simple.domain.user.entity.User;
import team7.simple.global.common.constant.ConstValue;

import javax.validation.Valid;

@Api(tags = {"[Open API] Authentication"})
@RequiredArgsConstructor
@RequestMapping("/open/auth")
@Controller
public class AuthOpenController {

    private final AuthService authService;

    @ApiOperation(value = "OPEN - 회원가입",
            notes = """
                    회원가입을 합니다.
                    \nparameter : 계정 아이디, 계정 비밀번호
                    \nresponse : X
                    """
    )
    @PreAuthorize("permitAll()")
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupRequestDto signupRequestDto) {
        authService.signup(signupRequestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @ApiOperation(value = "OPEN - 로그인",
            notes = """
                    로그인을 합니다.
                    \nparameter : 계정 아이디, 계정 비밀번호
                    \nresponse : 액세스 토큰, 리프레시 토큰
                    """
    )
    @PreAuthorize("permitAll()")
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto loginRequestDto) {
        return new ResponseEntity<>(authService.login(loginRequestDto), HttpStatus.OK);
    }


    @ApiOperation(value = "OPEN - 로그아웃",
            notes = """
                    로그아웃을 합니다.
                    \nparameter : X
                    \nresponse : X
                    """
    )
    @ApiImplicitParam(name = ConstValue.JWT_HEADER, value = "AccessToken", required = true, dataType = "String", paramType = "header")
    @PreAuthorize("isAuthenticated()")
    @PostMapping(value = "/logout")
    public ResponseEntity<?> logout(@RequestHeader(ConstValue.JWT_HEADER) String accessToken, @AuthenticationPrincipal User user) {
        authService.logout(accessToken, user);
        return new ResponseEntity<>(HttpStatus.OK);

    }


    @ApiOperation(value = "OPEN - 회원 탈퇴",
            notes = """
                    회원 탈퇴합니다.
                    \nparameter : X
                    \nresponse : X
                    """
    )
    @ApiImplicitParam(name = ConstValue.JWT_HEADER, value = "AccessToken", required = true, dataType = "String", paramType = "header")
    @PreAuthorize("isAuthenticated()")
    @PostMapping(value = "/withdrawal")
    public ResponseEntity<?> withdrawal(@RequestHeader(ConstValue.JWT_HEADER) String accessToken, @AuthenticationPrincipal User user) {
        authService.withdrawal(accessToken, user);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @ApiOperation(value = "OPEN - 액세스, 리프레시 토큰 재발급 ",
            notes = """
                    액세스 토큰과 리프레시 토큰을 재발급합니다.
                    \nparameter : X
                    \nresponse : 재발급 받은 액세스 토큰과 리프레시 토큰
                    """
    )
    @PreAuthorize("permitAll()")
    @PostMapping(value = "/reissue")
    public ResponseEntity<?> reissue(@RequestBody @Valid TokenRequestDto tokenRequestDto) {
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
