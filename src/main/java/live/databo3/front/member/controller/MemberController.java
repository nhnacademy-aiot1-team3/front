package live.databo3.front.member.controller;

import live.databo3.front.member.dto.*;
import live.databo3.front.member.service.MemberService;
import live.databo3.front.util.CookieUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.Duration;
import java.util.Objects;
import java.util.Optional;

/**
 * 회원 관련 작업을 처리하는 컨트롤러
 *
 * @author 이지현
 * @version 1.0.1
 */
@Slf4j
@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService service;

    /**
     * main 페이지로 이동
     * @return main으로 이동
     * @since 1.0.0
     */
    @GetMapping("/")
    public String getMain(){
        return "viewer/main";
    }

    /**
     * login 페이지로 이동
     * @return login으로 이동
     * @since 1.0.0
     */
    @GetMapping("/login")
    public String getLogin(){
        return "pre-login/login";
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
            Optional<ResponseDto<ResponseHeaderDto, TokenResponseDto>> result = service.doLogin(memberRequestDto);

            if(result.isPresent()) {
                TokenResponseDto token = result.get().getBody();


                String accesesToken = token.getAccessToken();
                String refreshToken = token.getRefreshToken();
                Long accessTokenExpireTime = token.getAccessTokenExpireTime();
                Long refreshTokenExpireTime = token.getRefreshTokenExpireTime();


                Cookie accesesCookie = new Cookie("access_token", accesesToken);
                Cookie refreshCookie = new Cookie("refresh_token", refreshToken);

                accesesCookie.setMaxAge((int) Duration.ofHours(1).toSeconds());
                accesesCookie.setPath("/");

                refreshCookie.setMaxAge((int) Duration.ofHours(1).toSeconds());
                refreshCookie.setPath("/");

                response.addCookie(accesesCookie);
                response.addCookie(refreshCookie);

//                cookie.setHttpOnly(true); // sonaqube에서 쿠키 저장할때 중요 정보가 들어간 쿠키는 httpOnly,Secure을 true로 설정해서 보호하라고 가이드 해줌
                model.addAttribute("message", "로그인 성공");
                model.addAttribute("searchUrl","page/main");
                return "alert";
            }
            throw new Exception();
        } catch(Exception e){
            model.addAttribute("message", "로그인 실패");
            model.addAttribute("searchUrl","pre-login/login");
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
        Cookie accessTokenCookie = CookieUtil.findCookie(request, "access_token");
        Cookie refreshTokenCookie = CookieUtil.findCookie(request, "refresh_token");
        if (Objects.isNull(accessTokenCookie)) {
            log.error("no access_token cookie found");
            return "pre-login/login";
        }
        if (Objects.isNull(refreshTokenCookie)) {
            log.error("no refresh_token cookie found");
            return "pre-login/login";
        }
        accessTokenCookie.setValue("");
        accessTokenCookie.setMaxAge(0);
        response.addCookie(accessTokenCookie);
        refreshTokenCookie.setValue("");
        refreshTokenCookie.setMaxAge(0);
        response.addCookie(refreshTokenCookie);
        return "redirect:/";
    }

    /**
     * 회원가입 페이지 이동
     * @return register로 이동
     * @since 1.0.0
     */
    @GetMapping("/register")
    public String getRegister(){
        return "pre-login/register";
    }

    /**
     * 회원 가입 기능 실행
     * @param memberRegisterRequest 사용자 회원가입 정보 (id, password, email)
     * @return login으로 이동
     * @since 1.0.0
     */
    @PostMapping("/register")
    public String postRegister(MemberRegisterRequest memberRegisterRequest) {
        service.doRegister(memberRegisterRequest);
        return "pre-login/login";
    }

    /**
     * 승인 대기화면 페이지로 이동
     * @return outstanding으로 이동
     * @since 1.0.0
     */
    @GetMapping("/outstanding")
    public String outstanding(){
        return "outstanding";
    }

    /**
     * 비밀번호 찾기(변경) 페이지로 이동
     * @return searchPassword 이동
     * @since 1.0.0
     */
    @GetMapping("/searchPassword")
    public String getSearchPassword(){
        return "pre-login/searchPassword";
    }

    /**
     * 비밀번호를 잊어버렸을때 email을 통해 새 비밀번호로 바꿔 준다
     * 작성한 이메일에 임시 비밀번호를 보내준다
     * @return check를 실패하면 alert로 이동, 비밀번호 맞을 시 mypage로 이동
     * @since 1.0.0
     * 아직 구현 다 못함
     */
    @PostMapping("/searchPassword")
    public String postSearchPassword(@RequestParam String nowPassword, @RequestParam String passwordCheck, @RequestParam String newPassword, Model model){
        if(!nowPassword.equals(passwordCheck)){
            model.addAttribute("message", "비밀번호를 잘못 입력하었습니다");
            model.addAttribute("searchUrl","pre-login/searchPassword");
            return "alert";
        }
        return "pre-login/changePassword";
    }
    /**
     * 비밀번호 변경 페이지로 이동
     * @return changePassword로 이동
     * @since 1.0.0
     */
    @GetMapping("/changePassword")
    public String getChangePassword(){
        return "changePassword";
    }
    /**
     * 현재 비밀번호에서 새 비밀번호로 바꿔 준다
     * 현재 비밀번호와 비밀번호 확인란이 서로 다르다면 비밀번호 입력이 잘못 되었음을 알린다
     * 확인란이 맞다면 기존 비밀번호를 새 비밀번호로 바꿔 저장한다
     * @return check를 실패하면 alert로 이동, 비밀번호 맞을 시 mypage로 이동
     * @since 1.0.0
     */
    @PostMapping("/changePassword")
    public String postChangePassword(@RequestParam String nowPassword, @RequestParam String passwordCheck, @RequestParam String newPassword, Model model){
        if(!nowPassword.equals(passwordCheck)){
            model.addAttribute("message", "비밀번호를 잘못 입력하었습니다");
            model.addAttribute("searchUrl","/page/changePassword");
            return "alert";
        }
        // TODO 비밀번호 확인하는 api 만들기
        return "changePassword";
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

    @PostMapping("/idCheck")
    public String idCheck(@RequestParam String id, Model model){
        if(service.doIdCheck(id)){
            model.addAttribute("message","이미 있는 아이디 입니다");
            model.addAttribute("searchUrl","register");
        }
        return "alert";
    }

    /**
     * 비밀번호 찾기 페이지로 이동
     * @return password로 이동
     * @since 1.0.0
     */
    @GetMapping("/passwordSearch")
    public String getFindPassword(){
        return "passwordSearch";
    }

    @GetMapping("/test")
    public String getTest(){
        return "charts";
    }
}
