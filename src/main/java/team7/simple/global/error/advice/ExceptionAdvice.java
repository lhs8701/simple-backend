package team7.simple.global.error.advice;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import team7.simple.global.common.response.dto.CommonResult;
import team7.simple.global.common.response.service.ResponseService;
import team7.simple.global.error.advice.exception.CUserNotFoundException;
import team7.simple.global.error.advice.exception.CWrongApproachException;
import team7.simple.global.error.advice.exception.CWrongPasswordException;


import javax.servlet.http.HttpServletRequest;

@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
public class ExceptionAdvice {
    private final ResponseService responseService;

//    @ExceptionHandler(Exception.class)
//    protected CommonResult defaultException(Exception e) {
//        return responseService.getFailResult(ErrorCode.INTERNAL_SERVER_ERROR.getCode(), ErrorCode.INTERNAL_SERVER_ERROR.getMessage());
//    }

    @ExceptionHandler(CWrongApproachException.class)
    protected CommonResult wrongApproachException(HttpServletRequest request, CWrongApproachException e) {
        return responseService.getFailResult
                (e.getErrorCode().getCode(), e.getErrorCode().getMessage());
    }

    @ExceptionHandler(CUserNotFoundException.class)
    protected CommonResult userNotFoundException(CUserNotFoundException e) {
        return responseService.getFailResult(e.getErrorCode().getCode(), e.getErrorCode().getMessage());
    }

    @ExceptionHandler(CWrongPasswordException.class)
    protected CommonResult wrongPasswordException(CWrongPasswordException e) {
        return responseService.getFailResult(e.getErrorCode().getCode(), e.getErrorCode().getMessage());
    }

}
