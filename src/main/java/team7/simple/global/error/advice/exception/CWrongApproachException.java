package team7.simple.global.error.advice.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import team7.simple.global.error.ErrorCode;

@Getter
public class CWrongApproachException extends RuntimeException{
    ErrorCode errorCode;

    public CWrongApproachException() {
        super();
        errorCode = ErrorCode.WRONG_APPROACH;
    }
}