package team7.simple.domain.study.error;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import team7.simple.domain.study.error.exception.CStudyNotFoundException;

@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
public class StudyExceptionAdvice {

    /***
     * 수강 리스트에 해당 강좌가 없을 경우 발생하는 예외
     * @param e CStudyNotFoundException
     * @return BAD_REQUEST 400
     */
    @ExceptionHandler(CStudyNotFoundException.class)
    protected ResponseEntity<?> handle(CStudyNotFoundException e) {
        log.error(e.getErrorCode().getMessage());
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
