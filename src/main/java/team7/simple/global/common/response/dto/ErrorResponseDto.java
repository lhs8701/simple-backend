package team7.simple.global.common.response.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team7.simple.global.error.ErrorCode;

@Getter
@NoArgsConstructor
public class ErrorResponseDto {
    int code;
    String message;

    public ErrorResponseDto(ErrorCode errorCode){
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }
}
