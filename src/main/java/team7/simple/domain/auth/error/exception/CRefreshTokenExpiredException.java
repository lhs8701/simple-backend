package team7.simple.domain.auth.error.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import team7.simple.global.error.ErrorCode;

@Getter
@AllArgsConstructor
public class CRefreshTokenExpiredException extends RuntimeException{
    private final ErrorCode errorCode;
    public CRefreshTokenExpiredException(){
        super();
        errorCode = ErrorCode.REFRESH_TOKEN_EXPIRED_EXCPEPTION;
    }
}
