package com.front.member.controller;

import com.front.member.dto.MemberRequestDto;
import com.front.member.dto.ResponseDto;
import com.front.member.dto.ResponseHeaderDto;
import com.front.member.dto.TokenResponseDto;
import com.front.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
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
            Optional<ResponseDto<ResponseHeaderDto, TokenResponseDto>> result = memberService.doLogin(memberRequestDto);

            if(result.isPresent()) {
                String authorization = result.get().getBody().getAccessToken();

                Cookie cookie = new Cookie("token", authorization);
                response.addCookie(cookie);
//                model.addAttribute("message", "로그인 성공");
//                model.addAttribute("searchUrl","main");
                return "redirect:/";
            }
            throw new Exception();
        } catch(Exception e){
//            model.addAttribute("message", "로그인 실패");
//            model.addAttribute("searchUrl","login");
            return "login";
        }
    }

    @GetMapping("/logout")
    public String getLogout(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        for(Cookie cookie : cookies) {
            if(cookie.getName().equals("token")) {
                cookie.setValue("");
                cookie.setMaxAge(0);
                response.addCookie(cookie);
            }
        }
        return "redirect:/";
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
