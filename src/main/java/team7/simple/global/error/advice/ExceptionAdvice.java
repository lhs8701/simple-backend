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

    /**
     * 공통 서버 에러
     */
//    @ExceptionHandler(Exception.class)
//    protected CommonResult defaultException(Exception e) {
//        return responseService.getFailResult(ErrorCode.INTERNAL_SERVER_ERROR.getCode(), ErrorCode.INTERNAL_SERVER_ERROR.getMessage());
//    }

    /**
     * 잘못된 형식일 때 발생 시키는 예외
     */
    @ExceptionHandler(CIllegalArgumentException.class)
    protected CommonResult illegalArgumentException(CIllegalArgumentException e) {
        return responseService.getFailResult
                (e.getErrorCode().getCode(), e.getErrorCode().getMessage());
    }

    @ExceptionHandler(CWrongApproachException.class)
    protected CommonResult wrongApproachException(CWrongApproachException e) {
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
    protected CommonResult refreshTokenException(CRefreshTokenInvalidException e) {
        return responseService.getFailResult
                (e.getErrorCode().getCode(), e.getErrorCode().getMessage());
    }

    /**
     * 리프레쉬 토큰이 만료되었을 경우
     */
    @ExceptionHandler(CRefreshTokenExpiredException.class)
    protected CommonResult refreshTokenExpiredException(CRefreshTokenExpiredException e) {
        return responseService.getFailResult
                (e.getErrorCode().getCode(), e.getErrorCode().getMessage());
    }

    /***
     * 기 가입자 에러
     */
    @ExceptionHandler(CUserExistException.class)
    protected CommonResult userExistException(CUserExistException e) {
        return responseService.getFailResult
                (e.getErrorCode().getCode(), e.getErrorCode().getMessage());
    }

    /**
     * 전달한 Jwt 이 정상적이지 않은 경우 발생 시키는 예외
     */
    @ExceptionHandler(CAuthenticationEntryPointException.class)
    protected CommonResult authenticationEntrypointException(CAuthenticationEntryPointException e) {
        return responseService.getFailResult
                (e.getErrorCode().getCode(), e.getErrorCode().getMessage());
    }

    /**
     * Security - 권한이 없는 리소스를 요청한 경우 발생 시키는 예외
     */
    @ExceptionHandler(CAccessDeniedException.class)
    protected CommonResult accessDeniedException(CAccessDeniedException e) {
        return responseService.getFailResult
                (e.getErrorCode().getCode(), e.getErrorCode().getMessage());
    }

    /**
     * Security - JWT 서명이 잘못되었을 때 발생 시키는 예외
     */
    @ExceptionHandler(CWrongTypeTokenException.class)
    protected CommonResult wrongTypeTokenException(CWrongTypeTokenException e) {
        return responseService.getFailResult
                (e.getErrorCode().getCode(), e.getErrorCode().getMessage());
    }
    /**
     * Security - 토큰이 만료되었을 때 발생 시키는 예외
     */
    @ExceptionHandler(CExpiredTokenException.class)
    protected CommonResult expiredTokenException(CExpiredTokenException e) {
        return responseService.getFailResult
                (e.getErrorCode().getCode(), e.getErrorCode().getMessage());
    }
    /**
     * Security - 지원하지 않는 토큰일 때 발생 시키는 예외
     */
    @ExceptionHandler(CUnsupportedTokenException.class)
    protected CommonResult unsupportedTokenException(CUnsupportedTokenException e) {
        return responseService.getFailResult
                (e.getErrorCode().getCode(), e.getErrorCode().getMessage());
    }
}
