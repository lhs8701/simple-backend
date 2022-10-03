package team7.simple.global.error.advice;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import team7.simple.global.common.response.dto.CommonResult;
import team7.simple.global.common.response.service.ResponseService;
import team7.simple.global.error.advice.exception.*;


import javax.servlet.http.HttpServletRequest;

@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
public class ExceptionAdvice {
    private final ResponseService responseService;

//    @ExceptionHandler(Exception.class)
//    protected CommonResult defaultException(Exception e) {
//        return responseService.getFailResult(ErrorCode.INTERNAL_SERVER_ERROR.getCode(), ErrorCode.INTERNAL_SERVER_ERROR.getMessage());
//    }

    @ExceptionHandler(CWrongApproachException.class)
    protected CommonResult wrongApproachException(HttpServletRequest request, CWrongApproachException e) {
        return responseService.getFailResult
                (e.getErrorCode().getCode(), e.getErrorCode().getMessage());
    }

    @ExceptionHandler(CUserNotFoundException.class)
    protected CommonResult userNotFoundException(CUserNotFoundException e) {
        return responseService.getFailResult(e.getErrorCode().getCode(), e.getErrorCode().getMessage());
    }

    @ExceptionHandler(CWrongPasswordException.class)
    protected CommonResult wrongPasswordException(CWrongPasswordException e) {
        return responseService.getFailResult(e.getErrorCode().getCode(), e.getErrorCode().getMessage());
    }

    /**
     * refresh token이 잘못되었을 경우
     */
    @ExceptionHandler(CRefreshTokenInvalidException.class)
    protected CommonResult refreshTokenException(HttpServletRequest request, CRefreshTokenInvalidException e) {
        return responseService.getFailResult
                (e.getErrorCode().getCode(), e.getErrorCode().getMessage());
    }

    /**
     * 리프레쉬 토큰이 만료되었을 경우
     */
    @ExceptionHandler(CRefreshTokenExpiredException.class)
    protected CommonResult refreshTokenExpiredException(HttpServletRequest request, CRefreshTokenExpiredException e) {
        return responseService.getFailResult
                (e.getErrorCode().getCode(), e.getErrorCode().getMessage());
    }

    /***
     * 기 가입자 에러
     */
    @ExceptionHandler(CUserExistException.class)
    protected CommonResult userExistException(HttpServletRequest request, CUserExistException e) {
        return responseService.getFailResult
                (e.getErrorCode().getCode(), e.getErrorCode().getMessage());
    }
}
