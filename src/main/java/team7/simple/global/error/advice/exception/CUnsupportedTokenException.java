package team7.simple.global.error.advice.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import team7.simple.global.error.ErrorCode;

@AllArgsConstructor
@Getter
public class CUnsupportedTokenException extends RuntimeException {
    private final ErrorCode errorCode;

    public CUnsupportedTokenException() {
        super();
        errorCode = ErrorCode.UNSUPPORTED_TOKEN_EXCEPTION;
    }
}
