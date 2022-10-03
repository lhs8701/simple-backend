package team7.simple.global.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    USER_NOT_FOUND_EXCEPTION(-1, "해당 회원을 조회할 수 없습니다."),
    WRONG_PASSWORD_EXCEPTION(-2, "잘못된 비밀번호 입니다."),

    REFRESH_TOKEN_INVALID_EXCEPTION(-1007, "리프레쉬 토큰이 잘못되었습니다."),
    REFRESH_TOKEN_EXPIRED_EXCPEPTION(-1014, "리프레쉬 토큰이 만료되었습니다. 로그인을 다시 해주세요."),
    AUTHENTICATION_ENTRY_POINT_EXCEPTION(-1002, "해당 리소스에 접근하기 위한 권한이 없습니다."), //정상적으로 Jwt이 제대로 오지 않은 경우

    USER_EXIST_EXCEPTION(-1006, "이미 가입된 계정입니다. 로그인을 해주세요"),

    WRONG_APPROACH(-9998, "잘못된 접근입니다."),
    INTERNAL_SERVER_ERROR(-9999, "서버 에러입니다.");



    private int code;
    private String message;
}
