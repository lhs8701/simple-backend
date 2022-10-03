package team7.simple.global.error.advice.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import team7.simple.global.error.ErrorCode;

@AllArgsConstructor
@Getter
public class CCourseNotFoundException extends RuntimeException {
    private final ErrorCode errorCode;

    public CCourseNotFoundException() {
        super();
        errorCode = ErrorCode.COURSE_NOT_FOUND_EXCEPTION;
    }
}
