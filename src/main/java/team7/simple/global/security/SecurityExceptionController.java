package team7.simple.global.security;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;
import team7.simple.global.common.response.dto.CommonResult;
import team7.simple.global.error.advice.exception.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/exception")
@ApiIgnore
public class SecurityExceptionController {

    @GetMapping("/entrypoint")
    public CommonResult entrypointException() {
        throw new CAuthenticationEntryPointException();
    }
    @GetMapping("/entrypoint/illegal")
    public CommonResult illegalArgumentException() {
        throw new CIllegalArgumentException();
    }
    @GetMapping("/entrypoint/wrong")
    public CommonResult wrongTypeTokenException() {
        throw new CWrongTypeTokenException();
    }
    @GetMapping("/entrypoint/expired")
    public CommonResult expiredTokenException() {
        throw new CExpiredTokenException();
    }
    @GetMapping("/entrypoint/unsupported")
    public CommonResult unsupportedTokenException() {
        throw new CUnsupportedTokenException();
    }

    @GetMapping("/accessDenied")
    public CommonResult accessDeniedException() {
        throw new CAccessDeniedException();
    }
}