package team7.simple.domain.auth.error.exception;

import lombok.Getter;
import team7.simple.global.error.ErrorCode;


@Getter
public class CRefreshTokenInvalidException extends RuntimeException {
    private final ErrorCode errorCode;
    public CRefreshTokenInvalidException(){
        super();
        errorCode = ErrorCode.REFRESH_TOKEN_INVALID_EXCEPTION;
    }
}
