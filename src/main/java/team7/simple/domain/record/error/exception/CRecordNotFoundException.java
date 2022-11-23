package team7.simple.domain.record.error.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import team7.simple.global.error.ErrorCode;

@AllArgsConstructor
@Getter
public class CRecordNotFoundException extends RuntimeException {
    private final ErrorCode errorCode;

    public CRecordNotFoundException() {
        super();
        errorCode = ErrorCode.NO_RECORD_EXCEPTION;
    }
}
