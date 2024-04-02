package live.databo3.front.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 에러 발생시 이곳에서 잡아서 에러 종류에 따라 각각의 method를 실행한다
 * @author 나채현
 * @version 1.0.0
 */
@Slf4j
@ControllerAdvice
public class ErrorHandler {
    /**
     * 만일 401로 에러 발생시 토큰 재발급
     * @param response 응답 객체
     * @param request 요청 객체
     * @return 기존의 uri로 redirect
     */
    @ExceptionHandler(Exception.class)
    public String errorCheck(HttpServletResponse response, HttpServletRequest request) {
        log.info(response.getStatus() + ", " +request.getRequestURI());
        if(response.getStatus() == 401){
            //토큰 재발급 요청 메서드
        }
          return "redirect:" + request.getRequestURI();
    }
}
