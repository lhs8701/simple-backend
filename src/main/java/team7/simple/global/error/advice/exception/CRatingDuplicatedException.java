package team7.simple.global.error.advice.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import team7.simple.global.error.ErrorCode;

@Slf4j
@Getter
@AllArgsConstructor
public class CRatingDuplicatedException extends RuntimeException { //정상적으로 Jwt이 제대로 오지 않은 경우
    private final ErrorCode errorCode;

    public CRatingDuplicatedException() {
        super();
        errorCode = ErrorCode.WRONG_REFRESH_TOKEN_EXCEPTION;
    }
}



