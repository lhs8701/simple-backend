package team7.simple.domain.enroll.error.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import team7.simple.global.error.ErrorCode;

@AllArgsConstructor
@Getter
public class CUserNotEnrolledException extends RuntimeException {
    private final ErrorCode errorCode;

    public CUserNotEnrolledException() {
        super();
        errorCode = ErrorCode.USER_NOT_ENROLLED_EXCEPTION;
    }
}
