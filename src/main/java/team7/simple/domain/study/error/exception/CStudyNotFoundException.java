package team7.simple.domain.study.error.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import team7.simple.global.error.ErrorCode;

@AllArgsConstructor
@Getter
public class CStudyNotFoundException extends RuntimeException {
    private final ErrorCode errorCode;

    public CStudyNotFoundException() {
        super();
        errorCode = ErrorCode.USER_NOT_ACTIVE_EXCEPTION;
    }
}
