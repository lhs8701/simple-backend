package team7.simple.global.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    USER_NOT_FOUND_EXCEPTION(-1, "해당 회원을 조회할 수 없습니다."),
    WRONG_PASSWORD_EXCEPTION(-2, "잘못된 비밀번호 입니다."),


    WRONG_APPROACH(-9998, "잘못된 접근입니다."),
    INTERNAL_SERVER_ERROR(-9999, "서버 에러입니다.");


    private int code;
    private String message;
}
