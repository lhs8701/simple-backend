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
     * 잘못된 형식일 때 발생시키는 예외
     */
    @ExceptionHandler(CIllegalArgumentException.class)
    protected CommonResult illegalArgumentException(CIllegalArgumentException e) {
        return responseService.getFailResult
                (e.getErrorCode().getCode(), e.getErrorCode().getMessage());
    }



    /**
     * Security - 권한이 없는 리소스를 요청한 경우 발생시키는 예외
     */
    @ExceptionHandler(CAccessDeniedException.class)
    protected CommonResult accessDeniedException(CAccessDeniedException e) {
        return responseService.getFailResult
                (e.getErrorCode().getCode(), e.getErrorCode().getMessage());
    }

    /**
     * Security - JWT 서명이 잘못되었을 때 발생시키는 예외
     */
    @ExceptionHandler(CWrongTypeTokenException.class)
    protected CommonResult wrongTypeTokenException(CWrongTypeTokenException e) {
        return responseService.getFailResult
                (e.getErrorCode().getCode(), e.getErrorCode().getMessage());
    }
    /**
     * Security - 토큰이 만료되었을 때 발생시키는 예외
     */
    @ExceptionHandler(CExpiredTokenException.class)
    protected CommonResult expiredTokenException(CExpiredTokenException e) {
        return responseService.getFailResult
                (e.getErrorCode().getCode(), e.getErrorCode().getMessage());
    }
    /**
     * Security - 지원하지 않는 토큰일 때 발생시키는 예외
     */
    @ExceptionHandler(CUnsupportedTokenException.class)
    protected CommonResult unsupportedTokenException(CUnsupportedTokenException e) {
        return responseService.getFailResult
                (e.getErrorCode().getCode(), e.getErrorCode().getMessage());
    }

    /**
     * 잘못된 접근시 발생시키는 예외
     */
    @ExceptionHandler(CWrongApproachException.class)
    protected CommonResult wrongApproachException(CWrongApproachException e) {
        return responseService.getFailResult
                (e.getErrorCode().getCode(), e.getErrorCode().getMessage());
    }

    /**
     * 해당 유저를 찾을 수 없을 경우 발생시키는 예외
     */
    @ExceptionHandler(CUserNotFoundException.class)
    protected CommonResult userNotFoundException(CUserNotFoundException e) {
        return responseService.getFailResult(e.getErrorCode().getCode(), e.getErrorCode().getMessage());
    }

    /**
     * 패스워드가 불일치할 경우 발생시키는 예외
     */
    @ExceptionHandler(CWrongPasswordException.class)
    protected CommonResult wrongPasswordException(CWrongPasswordException e) {
        return responseService.getFailResult(e.getErrorCode().getCode(), e.getErrorCode().getMessage());
    }

    /**
     * 해당 강좌를 찾을 수 없을 경우 발생시키는 예외
     */
    @ExceptionHandler(CCourseNotFoundException.class)
    protected CommonResult courseNotFoundException(CCourseNotFoundException e) {
        return responseService.getFailResult(e.getErrorCode().getCode(), e.getErrorCode().getMessage());
    }

    /**
     * refresh token이 잘못되었을 경우 발생시키는 예외
     */
    @ExceptionHandler(CRefreshTokenInvalidException.class)
    protected CommonResult refreshTokenException(CRefreshTokenInvalidException e) {
        return responseService.getFailResult
                (e.getErrorCode().getCode(), e.getErrorCode().getMessage());
    }

    /**
     * 리프레쉬 토큰이 만료되었을 경우 발생시키는 예외
     */
    @ExceptionHandler(CRefreshTokenExpiredException.class)
    protected CommonResult refreshTokenExpiredException(CRefreshTokenExpiredException e) {
        return responseService.getFailResult
                (e.getErrorCode().getCode(), e.getErrorCode().getMessage());
    }

    /***
     * 해당 계정이 이미 가입되어 있는 경우 발생시키는 예외
     */
    @ExceptionHandler(CUserExistException.class)
    protected CommonResult userExistException(CUserExistException e) {
        return responseService.getFailResult
                (e.getErrorCode().getCode(), e.getErrorCode().getMessage());
    }


}
