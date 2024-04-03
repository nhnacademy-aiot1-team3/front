package com.front.oauth.controller;

import com.front.member.dto.ResponseDto;
import com.front.member.dto.ResponseHeaderDto;
import com.front.member.dto.TokenResponseDto;
import com.front.oauth.service.OAuthService;
import com.front.oauth.service.OAuthServiceResolver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

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
    public String oauthLogin(@PathVariable("domain") String domain, String code) {
        OAuthService oAuthService = oAuthServiceResolver.getOAuthService(domain);

        ResponseDto<ResponseHeaderDto, TokenResponseDto> responseDto = oAuthService.doOAuthLogin(domain,code);

        log.info("{}", responseDto);

        return "redirect:/";
    }
}
