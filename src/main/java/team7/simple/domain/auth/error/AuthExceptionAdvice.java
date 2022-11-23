package team7.simple.domain.auth.error;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import team7.simple.domain.auth.error.exception.*;
import team7.simple.domain.auth.error.exception.CConflictUsageException;


@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
public class AuthExceptionAdvice {

    /**
     * JWT 서명이 잘못되었을 때 발생시키는 예외
     */
    @ExceptionHandler(CWrongTypeTokenException.class)
    protected ResponseEntity<?> handle(CWrongTypeTokenException e) {
        log.error(e.getErrorCode().getMessage());
        return new ResponseEntity<>(e.getErrorCode(), HttpStatus.NOT_FOUND);
    }

    /**
     * 토큰이 만료되었을 때 발생시키는 예외
     */
    @ExceptionHandler(CExpiredTokenException.class)
    protected ResponseEntity<?> handle(CExpiredTokenException e) {
        log.error(e.getErrorCode().getMessage());
        return new ResponseEntity<>(e.getErrorCode(), HttpStatus.NOT_FOUND);
    }

    /**
     * 권한이 없는 리소스를 요청한 경우 발생시키는 예외
     */
    @ExceptionHandler(CAccessDeniedException.class)
    protected ResponseEntity<?> handle(CAccessDeniedException e) {
        return new ResponseEntity<>(e.getErrorCode(), HttpStatus.NOT_FOUND);
    }

    /**
     * 같은 계정으로 플레이어를 동시에 이용할 경우 발생시키는 예외
     * @param e CConflictUsageException
     * @return CONFLICT 409
     */
    @ExceptionHandler(CConflictUsageException.class)
    protected ResponseEntity<?> handle(CConflictUsageException e) {
        log.error(e.getErrorCode().getMessage());
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }
}
