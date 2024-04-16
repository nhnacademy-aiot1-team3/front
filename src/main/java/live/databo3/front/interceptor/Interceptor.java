package live.databo3.front.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * controller 진입 전후에 실행하고 싶은 로직을 정의할 수 있는 인터셉터
 * @author 나채현
 * @version 1.0.0
 */
@Component
public class Interceptor implements HandlerInterceptor {
      /**
     * 요청이 controller 진입 전에 호출되는 메소드
     * @param request 요청 객체
     * @param response 응답 객체
     * @param handler 핸들러 객체
     * @return true를 반환하여 요청처리가 계속 진행
     * @throws Exception 예외 처리를 위한 선언
     * @since 1.0.0
     */
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        Cookie[] cookies = request.getCookies();
//        if (cookies != null) {
//            for (Cookie cookie : cookies) {
//                if (cookie.getName().equals("token")) {
//                    String token = cookie.getName();
//                    request.setAttribute("token", token);
//                    break;
//                }
//            }
//        }
//        return true;
//    }
}
