package team7.simple.domain.record.error.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import team7.simple.global.error.ErrorCode;

@Getter
@AllArgsConstructor
public class CRatingNotFoundException extends RuntimeException{
    private final ErrorCode errorCode;
    public CRatingNotFoundException(){
        super();
        errorCode = ErrorCode.RATING_NOT_FOUND_EXCEPTION;
    }
}
