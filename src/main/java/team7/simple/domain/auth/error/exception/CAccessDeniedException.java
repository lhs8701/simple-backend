package team7.simple.domain.auth.error.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import team7.simple.global.error.ErrorCode;

@AllArgsConstructor
@Getter
public class CAccessDeniedException extends RuntimeException {
    private final ErrorCode errorCode;

    public CAccessDeniedException() {
        super();
        errorCode = ErrorCode.ACCESS_DENIED;
    }
}
