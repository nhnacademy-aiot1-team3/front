package com.front.member.controller;

import com.front.member.dto.MemberRequestDto;
import com.front.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    @GetMapping("/")
    public String getMain(){
        return "main";
    }

    @GetMapping("/login")
    public String getLogin(){
        return "login";
    }

    @PostMapping("/login")
    public String postLogin(HttpServletResponse response, MemberRequestDto memberRequestDto, Model model) {
        try {
            Optional<String> result = memberService.doLogin(memberRequestDto);

            if(result.isPresent()) {
                String authorization = result.get();
                Cookie cookie = new Cookie("token", authorization);
                response.addCookie(cookie);

                model.addAttribute("message", "로그인 성공");
                model.addAttribute("searchUrl","main");
                return "alert";
            }
            throw new Exception();
        } catch(Exception e){
            model.addAttribute("message", "로그인 실패");
            model.addAttribute("searchUrl","login");
            return "alert";
        }
    }

    @GetMapping("/register")
    public String getRegister(){
        return "register";
    }

    @PostMapping("/register")
    public String postRegister(MemberRequestDto memberRequestDto) {
        memberService.doRegister(memberRequestDto);
        return "login";
    }
}
