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
    public void entrypointException() {
        throw new CAuthenticationEntryPointException();
    }
    @GetMapping("/entrypoint/illegal")
    public void illegalArgumentException() {
        throw new CIllegalArgumentException();
    }
    @GetMapping("/entrypoint/wrong")
    public void wrongTypeTokenException() {
        throw new CWrongTypeTokenException();
    }
    @GetMapping("/entrypoint/expired")
    public void expiredTokenException() {
        throw new CExpiredTokenException();
    }
    @GetMapping("/entrypoint/unsupported")
    public void unsupportedTokenException() {
        throw new CUnsupportedTokenException();
    }
    @GetMapping("/entrypoint/conflicted")
    public void loginConflictedException() {
        throw new CLoginConflictException();
    }
    @GetMapping("/accessDenied")
    public void accessDeniedException() {
        throw new CAccessDeniedException();
    }
}