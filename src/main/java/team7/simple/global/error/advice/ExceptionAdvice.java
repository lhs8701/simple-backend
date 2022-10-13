package team7.simple.global.error.advice;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import team7.simple.global.common.response.dto.CommonResult;
import team7.simple.global.common.response.service.ResponseService;
import team7.simple.global.error.ErrorCode;
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
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<?> defaultException(Exception e) {
        log.error(e.getMessage());
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * 잘못된 형식일 때 발생시키는 예외
     */
    @ExceptionHandler(CIllegalArgumentException.class)
    protected ResponseEntity<?> handle(CIllegalArgumentException e) {
        log.error(e.getErrorCode().getMessage());
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    /**
     * Security - 권한이 없는 리소스를 요청한 경우 발생시키는 예외
     */
    @ExceptionHandler(CAccessDeniedException.class)
    protected ResponseEntity<?> handle(CAccessDeniedException e) {
        log.error(e.getErrorCode().getMessage());
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    /**
     * Security - JWT 서명이 잘못되었을 때 발생시키는 예외
     */
    @ExceptionHandler(CWrongTypeTokenException.class)
    protected ResponseEntity<?> handle(CWrongTypeTokenException e) {
        log.error(e.getErrorCode().getMessage());
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    /**
     * Security - 토큰이 만료되었을 때 발생시키는 예외
     */
    @ExceptionHandler(CExpiredTokenException.class)
    protected ResponseEntity<?> handle(CExpiredTokenException e) {
        log.error(e.getErrorCode().getMessage());
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    /**
     * Security - 지원하지 않는 토큰일 때 발생시키는 예외
     */
    @ExceptionHandler(CUnsupportedTokenException.class)
    protected ResponseEntity<?> handle(CUnsupportedTokenException e) {
        log.error(e.getErrorCode().getMessage());
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    /**
     * 리프레시 토큰이 불일치할 경우 발생시키는 예외
     */
    @ExceptionHandler(CWrongRefreshTokenException.class)
    protected ResponseEntity<?> handle(CWrongRefreshTokenException e) {
        log.error(e.getErrorCode().getMessage());
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    /**
     * 잘못된 접근시 발생시키는 예외
     */
    @ExceptionHandler(CWrongApproachException.class)
    protected ResponseEntity<?> handle(CWrongApproachException e) {
        log.error(e.getErrorCode().getMessage());
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    /**
     * 해당 유저를 찾을 수 없을 경우 발생시키는 예외
     */
    @ExceptionHandler(CUserNotFoundException.class)
    protected ResponseEntity<?> handle(CUserNotFoundException e) {
        log.error(e.getErrorCode().getMessage());
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * 패스워드가 불일치할 경우 발생시키는 예외
     */
    @ExceptionHandler(CWrongPasswordException.class)
    protected ResponseEntity<?> handle(CWrongPasswordException e) {
        log.error(e.getErrorCode().getMessage());
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * 해당 강좌를 찾을 수 없을 경우 발생시키는 예외
     */
    @ExceptionHandler(CCourseNotFoundException.class)
    protected ResponseEntity<?> handle(CCourseNotFoundException e) {
        log.error(e.getErrorCode().getMessage());
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * 로그인 충돌시 발생시키는 예외
     */
    @ExceptionHandler(CLoginConflictException.class)
    protected ResponseEntity<?> handle(CLoginConflictException e) {
        log.error(e.getErrorCode().getMessage());
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    /**
     * refresh token이 잘못되었을 경우 발생시키는 예외
     */
    @ExceptionHandler(CRefreshTokenInvalidException.class)
    protected ResponseEntity<?> handle(CRefreshTokenInvalidException e) {
        log.error(e.getErrorCode().getMessage());
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    /**
     * 리프레쉬 토큰이 만료되었을 경우 발생시키는 예외
     */
    @ExceptionHandler(CRefreshTokenExpiredException.class)
    protected ResponseEntity<?> handle(CRefreshTokenExpiredException e) {
        log.error(e.getErrorCode().getMessage());
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    /***
     * 해당 계정이 이미 가입되어 있는 경우 발생시키는 예외
     */
    @ExceptionHandler(CUserExistException.class)
    protected ResponseEntity<?> handle(CUserExistException e) {
        log.error(e.getErrorCode().getMessage());
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    /***
     * 파일을 찾을 수 없을 경우
     */
    @ExceptionHandler(CFileNotFoundException.class)
    protected ResponseEntity<?> handle(CFileNotFoundException e) {
        log.error(e.getErrorCode().getMessage());
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /***
     * 수강 중인 강의가 아닐 경우
     */
    @ExceptionHandler(CStudyNotFoundException.class)
    protected ResponseEntity<?> handle(CStudyNotFoundException e) {
        log.error(e.getErrorCode().getMessage());
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
