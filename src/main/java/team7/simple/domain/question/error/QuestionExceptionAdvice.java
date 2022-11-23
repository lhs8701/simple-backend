package team7.simple.domain.question.error;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import team7.simple.domain.question.error.exception.CQuestionNotFoundException;
import team7.simple.global.common.response.dto.ErrorResponseDto;


@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
public class QuestionExceptionAdvice {

    /**
     * 해당 질문을 찾을 수 없을 경우 발생시키는 예외
     * @param e CQuestionNotFoundException
     * @return NOT_FOUND 404
     */
    @ExceptionHandler(CQuestionNotFoundException.class)
    protected ResponseEntity<?> handle(CQuestionNotFoundException e) {
        log.error(e.getErrorCode().getMessage());
        return new ResponseEntity<>(new ErrorResponseDto(e.getErrorCode()), HttpStatus.NOT_FOUND);
    }
}
