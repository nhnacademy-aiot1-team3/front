package com.front.login.controller;

import com.front.login.dto.LoginRequestDto;
import com.front.login.service.FrontService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class FrontController {
    private final FrontService frontService;
    @GetMapping("/")
    public String getMain(){
        return "main";
    }

    @GetMapping("/login")
    public String getLogin(){
        return "login";
    }

    @PostMapping("/login")
    public String postLogin(LoginRequestDto userDto, Model model) {
        try {
            frontService.doLogin(userDto);
            model.addAttribute("message", "로그인 성공");
            model.addAttribute("searchUrl","main");
            return "alert";

        } catch(Exception e){
            model.addAttribute("message", "로그인 실패");
            model.addAttribute("searchUrl","login");
            return "alert";
        }
    }
}
