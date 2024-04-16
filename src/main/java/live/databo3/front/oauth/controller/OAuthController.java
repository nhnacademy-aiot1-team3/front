package live.databo3.front.oauth.controller;

import live.databo3.front.member.dto.ResponseDto;
import live.databo3.front.member.dto.ResponseHeaderDto;
import live.databo3.front.member.dto.TokenResponseDto;
import live.databo3.front.oauth.service.OAuthService;
import live.databo3.front.oauth.service.OAuthServiceResolver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.time.Duration;

/**
 * OAuth 로그인 컨트롤러
 *
 * @author 양현성
 */
@Slf4j
@Controller
@RequestMapping("/oauth")
@RequiredArgsConstructor
public class OAuthController {

    private final OAuthServiceResolver oAuthServiceResolver;

    @GetMapping("/{domain}")
    public String oauthLoginForm(@PathVariable("domain") String domain) {
        OAuthService oAuthService = oAuthServiceResolver.getOAuthService(domain);
        return "redirect:"+oAuthService.makeLoginUrl();
    }


    @GetMapping("/{domain}/code")
    public String oauthLogin(@PathVariable("domain") String domain, String code, HttpServletResponse response) {
        OAuthService oAuthService = oAuthServiceResolver.getOAuthService(domain);

        ResponseDto<ResponseHeaderDto, TokenResponseDto> responseDto = oAuthService.doOAuthLogin(domain,code);

        log.info("{}", responseDto);

        String accessToken = responseDto.getBody().getAccessToken();
        Long accessTokenExpireTime = responseDto.getBody().getAccessTokenExpireTime();
        String refreshToken = responseDto.getBody().getRefreshToken();
        Long refreshTokenExpireTime = responseDto.getBody().getRefreshTokenExpireTime();

        Cookie accessTokenCookie = new Cookie("access_token", accessToken);
//        Cookie accessTokenExpireCookie = new Cookie("access_token_expire", accessTokenExpireTime);
        Cookie refreshTokenCookie = new Cookie("refresh_token", refreshToken );
//        Cookie refreshTokenExpireCookie = new Cookie("refresh_token_expire",  refreshTokenExpireTime);

        accessTokenCookie.setMaxAge((int)Duration.ofHours(1).toSeconds());
//        accessTokenExpireCookie.setMaxAge((int)Duration.ofHours(1).toSeconds());
        accessTokenCookie.setPath("/");
//        accessTokenExpireCookie.setPath("/");

        refreshTokenCookie.setMaxAge((int)Duration.ofDays(1).toSeconds());
//        refreshTokenExpireCookie.setMaxAge((int)Duration.ofDays(1).toSeconds());
        refreshTokenCookie.setPath("/");
//        refreshTokenExpireCookie.setPath("/");




        response.addCookie(accessTokenCookie);
//        response.addCookie(accessTokenExpireCookie);
        response.addCookie(refreshTokenCookie);
//        response.addCookie(refreshTokenExpireCookie);

        return "redirect:/";
    }
}
