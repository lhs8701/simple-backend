package team7.simple.domain.user.error;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import team7.simple.domain.user.error.exception.CUserExistException;
import team7.simple.domain.user.error.exception.CUserNotActiveException;
import team7.simple.domain.user.error.exception.CUserNotFoundException;
import team7.simple.domain.user.error.exception.CWrongPasswordException;

@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
public class UserExceptionAdvice {

    /***
     * 현재 시청중인 계정이 아닐 경우 (ActiveAccessToken이 없는 경우) 발생하는 예외
     * @param e CUserNotActiveException
     * @return BAD_REQUEST 400
     */
    @ExceptionHandler(CUserNotActiveException.class)
    protected ResponseEntity<?> handle(CUserNotActiveException e) {
        log.error(e.getErrorCode().getMessage());
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    /**
     * 회원가입 시, 이미 해당 계정이 존재하는 경우 발생하는 예외
     * @param e CUserExistException
     * @return CONFLICT 409
     */
    @ExceptionHandler(CUserExistException.class)
    protected ResponseEntity<?> handle(CUserExistException e) {
        log.error(e.getErrorCode().getMessage());
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    /**
     * 해당 사용자를 찾을 수 없는 경우 발생하는 예외
     * @param e CUserNotFoundException
     * @return NOT_FOUND 404
     */
    @ExceptionHandler(CUserNotFoundException.class)
    protected ResponseEntity<?> handle(CUserNotFoundException e) {
        log.error(e.getErrorCode().getMessage());
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * 회원가입 시, 비밀번호가 맞지 않는 경우 발생하는 예외
     * @param e CUserNotFoundException
     * @return NOT_FOUND 400
     */
    @ExceptionHandler(CWrongPasswordException.class)
    protected ResponseEntity<?> handle(CWrongPasswordException e) {
        log.error(e.getErrorCode().getMessage());
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
