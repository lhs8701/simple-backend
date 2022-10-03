package team7.simple.global.error.advice.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import team7.simple.global.error.ErrorCode;

@AllArgsConstructor
@Getter
public class CExpiredTokenException extends RuntimeException {
    private final ErrorCode errorCode;

    public CExpiredTokenException() {
        super();
        errorCode = ErrorCode.EXPIRED_TOKEN_EXCEPTION;
    }
}
