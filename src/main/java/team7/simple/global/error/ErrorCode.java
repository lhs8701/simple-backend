package team7.simple.global.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    /* 비즈니스 영역 */
    //1
    USER_NOT_FOUND_EXCEPTION(1000, "해당 회원을 조회할 수 없습니다."),
    COURSE_NOT_FOUND_EXCEPTION(1001, "해당 강좌를 찾을 수 없습니다."),
    UNIT_NOT_FOUND_EXCEPTION(1002, "해당 강의를 찾을 수 없습니다."),

    USER_NOT_ENROLLED_EXCEPTION(1003, "수강 중인 강의가 아닙니다."),
    RATING_NOT_FOUND_EXCEPTION(1004, "등록된 평점이 없습니다."),

    QUESTION_NOT_FOUND_EXCEPTION(1005, "해당 질문을 찾을 수 없습니다."),
    ANSWER_NOT_FOUND_EXCEPTION(1006, "해당 답변을 찾을 수 없습니다."),
    NO_RECORD_EXCEPTION(1007, "강의를 시청한 기록이 없습니다."),
    FILE_NOT_FOUND_EXCEPTION(1008, "해당 파일을 찾을 수 없습니다."),

    //2
    WRONG_PASSWORD_EXCEPTION(3000, "잘못된 비밀번호 입니다."),
    LOGIN_CONFLICT_EXCEPTION(3001, "로그인 충돌입니다."),
    USER_NOT_ACTIVE_EXCEPTION(3002, "현재 시청중인 계정이 아닙니다."),
    USER_EXIST_EXCEPTION(3003, "이미 가입된 계정입니다. 로그인을 해주세요"),


    /* 권한 & 인증 영역 */
    REFRESH_TOKEN_INVALID_EXCEPTION(5001, "리프레쉬 토큰이 잘못되었습니다."),
    REFRESH_TOKEN_EXPIRED_EXCEPTION(5002, "리프레쉬 토큰이 만료되었습니다. 로그인을 다시 해주세요."),
    AUTHENTICATION_ENTRY_POINT_EXCEPTION(5003, "해당 리소스에 접근하기 위한 권한이 없습니다."),
    ACCESS_DENIED(5004, "해당 리소스에 접근할 수 없는 권한입니다."),
    WRONG_REFRESH_TOKEN_EXCEPTION(5005, "refresh 토큰이 잘못되었습니다"),
    WRONG_TYPE_TOKEN_EXCEPTION(5006, "잘못된 Jwt 서명입니다."),
    EXPIRED_TOKEN_EXCEPTION(5007, "만료된 토큰입니다."),
    UNSUPPORTED_TOKEN_EXCEPTION(5008, "지원하지 않는 토큰입니다."),


    /* 외부 영역 */
    ILLEGAL_ARGUMENT_EXCEPTION(7004, "잘못된 형식입니다."),


    /* 서버 오류 */
    WRONG_APPROACH(9998, "잘못된 접근입니다."),
    INTERNAL_SERVER_ERROR(9999, "서버 에러입니다.");

    private int code;
    private String message;
}
