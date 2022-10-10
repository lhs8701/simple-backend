package team7.simple.global.error.advice.exception;

import lombok.Getter;
import team7.simple.global.error.ErrorCode;

@Getter
public class CLoginConflictException extends RuntimeException{
    private final ErrorCode errorCode;
    public CLoginConflictException(){
        super();
        errorCode = ErrorCode.LOGIN_CONFLICT_EXCEPTION;
    }
}
