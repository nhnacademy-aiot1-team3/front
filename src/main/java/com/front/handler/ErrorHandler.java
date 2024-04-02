package com.front.handler;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@ControllerAdvice
public class ErrorHandler {
    @ExceptionHandler(Exception.class)
    public String errorCheck(HttpServletResponse response, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        if (response.getStatus() == 401) {
        }
        return "redirect:" + request.getRequestURI();
    }
}
