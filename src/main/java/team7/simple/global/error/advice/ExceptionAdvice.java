package team7.simple.global.error.advice;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import team7.simple.global.error.advice.exception.CAccessDeniedException;
import team7.simple.global.common.response.dto.ErrorResponseDto;
import team7.simple.global.error.ErrorCode;
import team7.simple.global.error.advice.exception.*;

@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
public class ExceptionAdvice {

    /**
     * 서버 내부에서 에러가 발생할 경우
     * @param e Exception
     * @return INTERNAL_SERVER_ERROR 500
     */
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<?> defaultException(Exception e) {
        log.error("server error");
        e.printStackTrace();
        return new ResponseEntity<>(new ErrorResponseDto(ErrorCode.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * 입력 형식이 잘못되었을 때 발생하는 예외
     * @param e IllegalArgumentException
     * @return BAD_REQUEST 400
     */
    @ExceptionHandler(IllegalArgumentException.class)
    protected ResponseEntity<?> handle(IllegalArgumentException e) {
        log.error(ErrorCode.ILLEGAL_ARGUMENT_EXCEPTION.getMessage());
        return new ResponseEntity<>(new ErrorResponseDto(ErrorCode.ILLEGAL_ARGUMENT_EXCEPTION), HttpStatus.BAD_REQUEST);
    }

    /**
     * 잘못된 접근일 때 발생하는 예외
     * @param e CWrongApproach
     * @return FORBIDDEN 403
     */
    @ExceptionHandler(CWrongApproach.class)
    protected ResponseEntity<?> handle(CWrongApproach e) {
        ErrorCode errorCode = e.getErrorCode();

        log.error(errorCode.getMessage());
        return new ResponseEntity<>(new ErrorResponseDto(errorCode), errorCode.getStatusCode());
    }

    /**
     * 접근이 제한되었을 때 발생하는 예외
     * @param e CAccessDeniedException
     * @return BAD_REQUEST 400
     */
    @ExceptionHandler(CAccessDeniedException.class)
    protected ResponseEntity<?> handle(CAccessDeniedException e) {
        ErrorCode errorCode = e.getErrorCode();
        log.error(errorCode.getMessage());
        return new ResponseEntity<>(new ErrorResponseDto(errorCode), errorCode.getStatusCode());
    }
}
