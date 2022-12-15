package team7.simple.domain.user.error.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import team7.simple.global.error.ErrorCode;

@AllArgsConstructor
@Getter
public class CUserNotActiveException extends RuntimeException {
    private final ErrorCode errorCode;

    public CUserNotActiveException() {
        super();
        errorCode = ErrorCode.USER_NOT_ACTIVE_EXCEPTION;
    }
}
