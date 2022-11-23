package team7.simple.domain.file.error;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import team7.simple.domain.file.error.exception.CFileNotFoundException;
import team7.simple.global.common.response.dto.ErrorResponseDto;

@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
public class FileExceptionAdvice {

    /***
     * 해당 파일을 찾을 수 없을 경우 발생하는 예외
     * @param e CFileNotFoundException
     * @return NOT_FOUND 404
     */
    @ExceptionHandler(CFileNotFoundException.class)
    protected ResponseEntity<?> handle(CFileNotFoundException e) {
        log.error(e.getErrorCode().getMessage());
        return new ResponseEntity<>(new ErrorResponseDto(e.getErrorCode()), HttpStatus.NOT_FOUND);
    }
}
