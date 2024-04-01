package com.front.oauth.controller;

import com.front.oauth.service.GithubOAuthService;
import com.front.oauth.service.OAuthService;
import com.front.oauth.service.OAuthServiceResolver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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
    public String oauthLogin(@PathVariable("domain") String domain,String code) {
        log.info("{}","in");
        log.info("{}",code);
        OAuthService oAuthService = oAuthServiceResolver.getOAuthService(domain);

        String tokenRequestUrl = oAuthService.makeTokenRequestUrl(code);
        log.info("{}",tokenRequestUrl);

        String accessToken = oAuthService.getAccessToken(tokenRequestUrl);
        log.info("{}",accessToken);
        String userInfoRequestUrl = oAuthService.makeUserInfoRequestUrl();
        log.info("{}",userInfoRequestUrl);
        String userInfo = oAuthService.getUserInfo(userInfoRequestUrl, accessToken);
        log.info("{}",userInfo);

        return "";
    }
}
