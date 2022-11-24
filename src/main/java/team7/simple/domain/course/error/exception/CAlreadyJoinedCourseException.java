package team7.simple.domain.course.error.exception;

import lombok.Getter;
import team7.simple.global.error.ErrorCode;

@Getter
public class CAlreadyJoinedCourseException extends RuntimeException{
    private final ErrorCode errorCode;

    public CAlreadyJoinedCourseException() {
        super();
        errorCode = ErrorCode.ALREADY_JOINED_COURSE_EXCEPTION;
    }
}

