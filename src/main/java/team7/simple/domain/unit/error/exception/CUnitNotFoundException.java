package team7.simple.domain.unit.error.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import team7.simple.global.error.ErrorCode;

@AllArgsConstructor
@Getter
public class CUnitNotFoundException extends RuntimeException {
    private final ErrorCode errorCode;

    public CUnitNotFoundException() {
        super();
        errorCode = ErrorCode.UNIT_NOT_FOUND_EXCEPTION;
    }
}