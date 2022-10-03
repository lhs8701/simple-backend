package team7.simple.global.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import team7.simple.global.error.ErrorCode;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//스프링 시큐리티는 Servlet Dispatcher 앞단에 존재한다. 따라서 스프링이 제어할 수 없는 영역에서 발생하는 예외를 처리하기 위해선 스프링 시큐리티가 제공하는 AuthenticationEntryPoiint를 상속받아서 재정의
//예외가 발생한 경우 "/exception/entrypoint"으로 리다이렉트
@Component
@Slf4j
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        String exception = (String)request.getAttribute("exception");

        //잘못된 토큰
        if (exception.equals(String.valueOf(ErrorCode.ILLEGAL_ARGUMENT_EXCEPTION.getCode()))){
            response.sendRedirect("/exception/entrypoint/illegal");
        }
        //잘못된 서명의 토큰인 경우
       else if(exception.equals(String.valueOf(ErrorCode.WRONG_TYPE_TOKEN_EXCEPTION.getCode()))) {
            response.sendRedirect("/exception/entrypoint/wrong");
        }
        //토큰 만료된 경우
        else if(exception.equals(String.valueOf(ErrorCode.EXPIRED_TOKEN_EXCEPTION.getCode()))) {
            response.sendRedirect("/exception/entrypoint/expired");
        }
        //지원되지 않는 토큰인 경우
        else if(exception.equals(String.valueOf(ErrorCode.UNSUPPORTED_TOKEN_EXCEPTION.getCode()))) {
            response.sendRedirect("/exception/entrypoint/unsupported");
        }
        //접근 권한이 없는 경우
        else {
            response.sendRedirect("/exception/entrypoint");
        }
    }
}
