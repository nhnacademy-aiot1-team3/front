package com.front.member.controller;

import com.front.member.dto.*;
import com.front.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

/**
 * 회원 관련 작업을 처리하는 컨트롤러
 *
 * @author 이지현
 * @version 1.0.0
 */
@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    /**
     * main 페이지로 이동
     * @return main으로 이동
     * @since 1.0.0
     */
    @GetMapping("/")
    public String getMain(){
        return "main";
    }

    /**
     * login 페이지로 이동
     * @return login으로 이동
     * @since 1.0.0
     */
    @GetMapping("/login")
    public String getLogin(){
        return "login";
    }

    /**
     * 로그인 요청이 들어오면 gateway에 확인을 요청한다
     * 만일 성공시 token을 cookie로 저장, 로그인 성공이라는 message 저장, 이동할 위치 : main
     * 만일 실패시 로그인 실패라는 message 저장, 이동할 위치 login
     * @param response 응답 객체
     * @param memberRequestDto 사용자 가입 정보 (id, pw)
     * @param model key에 따라 객체 저장
     * @return alert로 이동
     * @since 1.0.0
     */
    @PostMapping("/login")
    public String postLogin(HttpServletResponse response, MemberRequestDto memberRequestDto, Model model) {
        try {
            Optional<ResponseDto<ResponseHeaderDto, TokenResponseDto>> result = memberService.doLogin(memberRequestDto);

            if(result.isPresent()) {
                String authorization = result.get().getBody().getAccessToken();

                Cookie cookie = new Cookie("token", authorization);
                response.addCookie(cookie);
                model.addAttribute("message", "로그인 성공");
                model.addAttribute("searchUrl","/");
                return "alert";
            }
            throw new Exception();
        } catch(Exception e){
            model.addAttribute("message", "로그인 실패");
            model.addAttribute("searchUrl","login");
            return "alert";
        }
    }

    /**
     * token이라는 이름의 쿠키 삭제
     * @param request 요청 객체
     * @param response 응답 객체
     * @return /로 redirect
     * @since 1.0.0
     */
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

    /**
     * 회원가입 페이지 이동
     * @return register로 이동
     * @since 1.0.0
     */
    @GetMapping("/register")
    public String getRegister(){
        return "register";
    }

    /**
     * 회원 가입 기능 실행
     * @param memberRegisterRequest 사용자 회원가입 정보 (id, password, email)
     * @return login으로 이동
     * @since 1.0.0
     */
    @PostMapping("/register")
    public String postRegister(MemberRegisterRequest memberRegisterRequest) {
        memberService.doRegister(memberRegisterRequest);
        return "login";
    }

    /**
     * jwt token 새로 발급
     * @param response 응답 객체
     * @param request 요청 객체
     * @return request에 저장되어있는 path로 이동
     * @since 1.0.0
     */
    @GetMapping("/token")
    public String token(HttpServletResponse response, HttpServletRequest request) {
        // jwt token 새로 발급
        System.out.println("response = " + response);
        return (String)request.getAttribute("path");
    }

    /**
     * profile 페이지로 이동
     * @return profile으로 이동
     * @since 1.0.0
     */
    @GetMapping("/profile")
    public String getProfile(){
        return "profile";
    }
}
