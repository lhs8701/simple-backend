package team7.simple.domain.user.error.exception;

import lombok.Getter;
import team7.simple.global.error.ErrorCode;

@Getter
public class CWrongPasswordException extends RuntimeException{
    private final ErrorCode errorCode;
    public CWrongPasswordException(){
        super();
        errorCode = ErrorCode.WRONG_PASSWORD_EXCEPTION;
    }
}
