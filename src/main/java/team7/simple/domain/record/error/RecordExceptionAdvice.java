package team7.simple.domain.record.error;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import team7.simple.domain.record.error.exception.CRatingNotFoundException;
import team7.simple.domain.record.error.exception.CRecordNotFoundException;
import team7.simple.global.common.response.dto.ErrorResponseDto;
import team7.simple.global.error.ErrorCode;

@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
public class RecordExceptionAdvice {

    /**
     * 평점을 찾을 수 없는 경우 발생하는 예외
     * @param e CRatingNotFoundException
     * @return NOT_FOUND 404
     */
    @ExceptionHandler(CRatingNotFoundException.class)
    protected ResponseEntity<?> handle(CRatingNotFoundException e) {
        ErrorCode errorCode = e.getErrorCode();

        log.error(errorCode.getMessage());
        return new ResponseEntity<>(new ErrorResponseDto(errorCode), errorCode.getStatusCode());
    }

    /**
     * 기록을 찾을 수 없는 경우 발생하는 예외
     * @param e CRecordNotFoundException
     * @return NOT_FOUND 404
     */
    @ExceptionHandler(CRecordNotFoundException.class)
    protected ResponseEntity<?> handle(CRecordNotFoundException e) {
        ErrorCode errorCode = e.getErrorCode();

        log.error(errorCode.getMessage());
        return new ResponseEntity<>(new ErrorResponseDto(errorCode), errorCode.getStatusCode());
    }
}
