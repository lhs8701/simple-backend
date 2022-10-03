package team7.simple.global.error.advice.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import team7.simple.global.error.ErrorCode;

@Getter
@AllArgsConstructor
public class CAccessDeniedException extends RuntimeException{
    private final ErrorCode errorCode;

    public CAccessDeniedException() {
        super();
        errorCode = ErrorCode.ACCESS_DENIED;
    }
}