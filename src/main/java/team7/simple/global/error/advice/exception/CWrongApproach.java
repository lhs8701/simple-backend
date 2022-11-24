package team7.simple.global.error.advice.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import team7.simple.global.error.ErrorCode;

@Getter
@AllArgsConstructor
public class CWrongApproach extends RuntimeException{
    private final ErrorCode errorCode;
    public CWrongApproach() {
        super();
        errorCode = ErrorCode.AUTHENTICATION_ENTRY_POINT_EXCEPTION;

    }
}
