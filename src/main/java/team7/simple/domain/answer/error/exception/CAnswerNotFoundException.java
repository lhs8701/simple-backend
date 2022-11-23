package team7.simple.domain.answer.error.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import team7.simple.global.error.ErrorCode;

@AllArgsConstructor
@Getter
public class CAnswerNotFoundException extends RuntimeException{
    private final ErrorCode errorCode;

    public CAnswerNotFoundException() {
        super();
        errorCode = ErrorCode.ANSWER_NOT_FOUND_EXCEPTION;
    }
}
