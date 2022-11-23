package team7.simple.domain.unit.error;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import team7.simple.domain.unit.error.exception.CUnitNotFoundException;
import team7.simple.global.common.response.dto.ErrorResponseDto;

@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
public class UnitExceptionAdvice {

    /***
     * 해당 유닛을 찾을 수 없는 경우 발생하는 예외
     * @param e CUnitNotFoundException
     * @return NOT_FOUND 404
     */
    @ExceptionHandler(CUnitNotFoundException.class)
    protected ResponseEntity<?> handle(CUnitNotFoundException e) {
        log.error(e.getErrorCode().getMessage());
        return new ResponseEntity<>(new ErrorResponseDto(e.getErrorCode()), HttpStatus.NOT_FOUND);
    }

}
