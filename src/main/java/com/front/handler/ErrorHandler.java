package com.front.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Slf4j
@ControllerAdvice
public class ErrorHandler {
    @ExceptionHandler(Exception.class)
    public String errorCheck(HttpServletResponse response, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        log.info(response.getStatus() + ", " +request.getRequestURI());
        String method = request.getMethod();
        if("POST".equals(method)){
            if(response.getStatus() == 401){
                //토큰 재발급 요청 메서드
            }
            String url = request.getRequestURI();
            Map<String, String[]> parameters = request.getParameterMap();
            for(String key : parameters.keySet()){
                log.info(key);
            }
            return "redirect:" + request.getRequestURI();
        }else{
            if(response.getStatus() == 401){
                //토큰 재발급 요청 메서드
            }
            return "redirect:" + request.getRequestURI();
        }
    }

//    @ExceptionHandler(Exception.class)
//    public void errorCheck(HttpServletResponse response, HttpServletRequest request) throws IOException, IOException {
//            String method = request.getMethod();
//
//            if ("POST".equals(method)) {
//                Map<String, String[]> parameters = request.getParameterMap();
//
//                String redirectUrl = request.getRequestURI(); // 현재 요청의 URI를 가져옴
//                response.sendRedirect(redirectUrl); // 클라이언트로 리다이렉트하여 요청을 재수행하도록 유도
//
//            } else {
//                String redirectUrl = request.getRequestURI(); // 현재 요청의 URI를 가져옴
//                response.sendRedirect(redirectUrl); // 클라이언트로 리다이렉트하여 요청을 재수행하도록 유도
//            }
//    }
}
