package team7.simple.domain.enroll.error;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import team7.simple.domain.enroll.error.exception.CUserNotEnrolledException;
import team7.simple.global.common.response.dto.ErrorResponseDto;
import team7.simple.global.error.ErrorCode;

@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
public class EnrollExceptionAdvice {

    /***
     * 수강 리스트에 해당 강좌가 없을 경우 발생하는 예외
     * @param e CStudyNotFoundException
     * @return BAD_REQUEST 400
     */
    @ExceptionHandler(CUserNotEnrolledException.class)
    protected ResponseEntity<?> handle(CUserNotEnrolledException e) {
        ErrorCode errorCode = e.getErrorCode();

        log.error(errorCode.getMessage());
        return new ResponseEntity<>(new ErrorResponseDto(errorCode), errorCode.getStatusCode());
    }
}
