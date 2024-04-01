package com.front.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@ControllerAdvice
public class ErrorHandler {
    @ExceptionHandler(Exception.class)
    public String errorCheck(HttpServletResponse response, HttpServletRequest request) {
        log.info(response.getStatus() + ", " +request.getRequestURI());
        if(response.getStatus() == 401){
            //토큰 재발급 요청 메서드
        }
          return "redirect:" + request.getRequestURI();
    }
}
