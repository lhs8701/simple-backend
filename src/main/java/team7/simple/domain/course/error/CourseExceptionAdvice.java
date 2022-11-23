package team7.simple.domain.course.error;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import team7.simple.domain.auth.error.exception.*;
import team7.simple.domain.course.error.exception.CCourseNotFoundException;
import team7.simple.domain.user.error.exception.CWrongPasswordException;


@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
public class CourseExceptionAdvice {

    /**
     * 해당 강좌를 찾을 수 없을 경우 발생시키는 예외
     * @param e CCourseNotFoundException
     * @return NOT_FOUND 404
     */
    @ExceptionHandler(CCourseNotFoundException.class)
    protected ResponseEntity<?> handle(CCourseNotFoundException e) {
        log.error(e.getErrorCode().getMessage());
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
