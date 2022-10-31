package team7.simple.global.error.advice.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import team7.simple.global.error.ErrorCode;

@AllArgsConstructor
@Getter
public class CQuestionNotFoundException extends RuntimeException {
    private final ErrorCode errorCode;

    public CQuestionNotFoundException() {
        super();
        errorCode = ErrorCode.UNIT_NOT_FOUND_EXCEPTION;
    }
}