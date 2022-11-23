package team7.simple.domain.auth.error.exception;

import lombok.Getter;
import team7.simple.global.error.ErrorCode;

@Getter
public class CWrongTypeTokenException extends RuntimeException{
    private final ErrorCode errorCode;
    public CWrongTypeTokenException(){
        super();
        this.errorCode = ErrorCode.WRONG_TYPE_TOKEN_EXCEPTION;
    }
}
