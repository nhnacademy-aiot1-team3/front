package live.databo3.front.member.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import live.databo3.front.member.adaptor.MemberAdaptor;
import live.databo3.front.member.dto.*;
import live.databo3.front.util.CookieUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.Duration;
import java.util.Objects;

/**
 * 회원 관련 작업을 처리하는 컨트롤러
 *
 * @author 이지현
 * @version 1.0.2
 */
@Slf4j
@Controller
@RequiredArgsConstructor
public class MemberController {
    private static final String LOGIN_PAGE = "pre_login/login";
    private static final String LOGIN_URL = "/login";
    private static final String REGISTER_PAGE = "pre_login/register";
    private static final String REGISTER_URL = "pre-login/register";
    private static final String ALERT="alert";
    private static final String ALERT_MESSAGE="message";
    private static final String ALERT_URL="searchUrl";

    private final MemberAdaptor memberAdaptor;

    /**
     * main 페이지로 이동
     * SecurityContext에 ContextHolder에 role을 확인하여 role에 따른 main page 이동
     * @return role에 따른 main으로 이동
     * @since 1.0.2
     */
    @GetMapping("/")
    public String getMain(Authentication authentication){
        if(authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))){
            return "admin/main";
        }else if(authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_OWNER"))){
            return "owner/main";
        }else if(authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_VIEWER"))){
            return "viewer/main";
        }
        return LOGIN_PAGE;
    }

    /**
     * login 페이지로 이동
     * @return login으로 이동
     * @since 1.0.0
     */
    @GetMapping("/login")
    public String getLogin(){
        return LOGIN_PAGE;
    }

    /**
     * 로그인 요청이 들어오면 gateway에 확인을 요청한다
     * 만일 성공시 accesesToken,refreshToken cookie로 저장, 로그인 성공이라는 message 저장, 이동할 위치 : main
     * 만일 실패시 로그인 실패라는 message, 이동할 위치 login
     * 실패 message는 아이디틀림,비밀번호틀림,상태코드 WAIT 등이 있음
     * @param response 응답 객체
     * @param memberRequestDto 사용자 가입 정보 (id, pw)
     * @param model key에 따라 객체 저장
     * @return alert로 이동
     * @since 1.0.1
     */
    @PostMapping("/login")
    public String postLogin(HttpServletResponse response, MemberRequestDto memberRequestDto, Model model) {
        try {
            ResponseDto<ResponseHeaderDto, TokenResponseDto> result = memberAdaptor.doLogin(memberRequestDto);
            if(result.getHeader().getResultMessage().equals("로그인 성공")) {
                TokenResponseDto token = result.getBody();

                String accesesToken = token.getAccessToken();
                String refreshToken = token.getRefreshToken();

                Cookie accesesCookie = new Cookie("access_token", accesesToken);
                Cookie refreshCookie = new Cookie("refresh_token", refreshToken);

                accesesCookie.setMaxAge((int) Duration.ofHours(1).toSeconds());
                accesesCookie.setPath("/");
                accesesCookie.setHttpOnly(true);
                refreshCookie.setMaxAge((int) Duration.ofHours(1).toSeconds());
                refreshCookie.setPath("/");
                refreshCookie.setHttpOnly(true);
                response.addCookie(accesesCookie);
                response.addCookie(refreshCookie);

                return "redirect:/";
            }
        } catch(HttpClientErrorException e){
            model.addAttribute(ALERT_MESSAGE, e.getStatusText());
            model.addAttribute(ALERT_URL,LOGIN_URL);
        } catch (Exception e) {
            model.addAttribute(ALERT_MESSAGE, "로그인에 실패하였습니다");
            model.addAttribute(ALERT_URL,LOGIN_URL);
        }
        return ALERT;
    }

    /**
     * token이라는 이름의 쿠키 삭제
     * @param request 요청 객체
     * @param response 응답 객체
     * @return /로 redirect
     * @since 1.0.1
     */
    @GetMapping("/logout")
    public String getLogout(HttpServletRequest request, HttpServletResponse response) {
        Cookie accessTokenCookie = CookieUtil.findCookie(request, "access_token");
        Cookie refreshTokenCookie = CookieUtil.findCookie(request, "refresh_token");
        if (Objects.nonNull(accessTokenCookie) && Objects.nonNull(refreshTokenCookie)) {
            accessTokenCookie.setValue("");
            accessTokenCookie.setMaxAge(0);
            response.addCookie(accessTokenCookie);
            refreshTokenCookie.setValue("");
            refreshTokenCookie.setMaxAge(0);
            response.addCookie(refreshTokenCookie);
        } else{
            log.error("no access_token or refresh_token cookie");
        }
        return "redirect:/login";
    }

    /**
     * 회원가입 페이지 이동
     * @return register로 이동
     * @since 1.0.0
     */
    @GetMapping("/register")
    public String getRegister(){
        return REGISTER_PAGE;
    }

    /**
     * 회원 가입 기능 실행
     * @param memberRegisterRequest 사용자 회원가입 정보 (id, password, email)
     * @return login으로 이동
     * @since 1.0.0
     */
    @PostMapping("/register")
    public String postRegister(MemberRegisterRequest memberRegisterRequest, Model model) {
        try{
            memberAdaptor.doRegister(memberRegisterRequest);
            model.addAttribute(ALERT_MESSAGE, "회원가입 성공");
            model.addAttribute(ALERT_URL,LOGIN_URL);
        }catch (Exception e) {
            model.addAttribute(ALERT_MESSAGE, "회원가입 실패");
            model.addAttribute(ALERT_URL,REGISTER_URL);
        }
        return ALERT;
    }

    /**
     * 비밀번호 찾기(변경) 페이지로 이동
     * @return searchPassword 이동
     * @since 1.0.0
     */
    @GetMapping("/search-password")
    public String getSearchPassword(){
        return "pre_login/search_password";
    }

    /**
     * 비밀번호를 잊어버렸을때 email을 통해 새 비밀번호로 바꿔 준다
     * 작성한 이메일에 임시 비밀번호를 보내준다
     * @return check를 실패하면 alert로 이동, 비밀번호 맞을 시 mypage로 이동
     * @since 1.0.0
     * 아직 구현 다 못함
     */
    @PostMapping("/search-password")
    public String postSearchPassword(@RequestParam String nowPassword, @RequestParam String passwordCheck, @RequestParam String newPassword, Model model){
        if(!nowPassword.equals(passwordCheck)){
            model.addAttribute(ALERT_MESSAGE, "비밀번호를 잘못 입력하었습니다");
            model.addAttribute(ALERT_URL,"/search-password");
        }
        return ALERT;
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
            model.addAttribute(ALERT_MESSAGE, "비밀번호를 잘못 입력하었습니다");
            model.addAttribute(ALERT_URL,"/page/changePassword");
        }
        // TODO 비밀번호 확인하는 api 만들기
        return ALERT;
    }
}
