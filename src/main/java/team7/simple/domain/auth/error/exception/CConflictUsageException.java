package team7.simple.domain.auth.error.exception;

import lombok.Getter;
import team7.simple.global.error.ErrorCode;

@Getter
public class CConflictUsageException extends RuntimeException{
    private final ErrorCode errorCode;
    public CConflictUsageException(){
        super();
        errorCode = ErrorCode.LOGIN_CONFLICT_EXCEPTION;
    }
}
