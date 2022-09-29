package team7.simple.global.error.advice.exception;


import lombok.Getter;
import lombok.NoArgsConstructor;
import team7.simple.global.error.ErrorCode;

@Getter
public class CUserNotFoundException extends RuntimeException{
    private final ErrorCode errorCode;
    public CUserNotFoundException(){
        super();
        this.errorCode = ErrorCode.USER_NOT_FOUND_EXCEPTION;
    }
}
