package team7.simple.global.common.response.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import team7.simple.global.error.ErrorCode;

@Getter
@NoArgsConstructor
public class ErrorResponseDto {
    int code;
    String message;
    int statusCode;

    public ErrorResponseDto(ErrorCode errorCode){
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
        this.statusCode = errorCode.getStatusCode().value();
    }
}
