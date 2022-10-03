package team7.simple.global.error.advice.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import team7.simple.global.error.ErrorCode;

@AllArgsConstructor
@Getter
public class CIllegalArgumentException extends RuntimeException {
    private final ErrorCode errorCode;

    public CIllegalArgumentException() {
        super();
        errorCode = ErrorCode.ILLEGAL_ARGUMENT_EXCEPTION;
    }
}
