package team7.simple.global.error.advice.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import team7.simple.global.error.ErrorCode;

@AllArgsConstructor
@Getter
public class CNoRecordException extends RuntimeException {
    private final ErrorCode errorCode;

    public CNoRecordException() {
        super();
        errorCode = ErrorCode.NO_RECORD_EXCEPTION;
    }
}
