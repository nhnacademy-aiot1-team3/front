package com.front.oauth.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.front.oauth.adaptor.OAuthAdaptor;
import com.front.oauth.dto.GithubOAuthToken;
import com.front.oauth.properties.GithubOAuthProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@Service
@RequiredArgsConstructor
public class GithubOAuthService implements OAuthService{
    private final GithubOAuthProperties githubOAuthProperties;
    private final ObjectMapper objectMapper;
    private final OAuthAdaptor oauthAdaptor;


    public String makeLoginUrl() {
        return UriComponentsBuilder.newInstance()
                .scheme("https")
                .host("github.com")
                .path("login/oauth/authorize")
                .queryParam("client_id", githubOAuthProperties.getClientId())
                .build().toUriString();
    }

    public String makeTokenRequestUrl(String code) {
        return UriComponentsBuilder.newInstance()
                .scheme("https")
                .host("github.com")
                .path("login/oauth/access_token")
                .queryParam("client_id", githubOAuthProperties.getClientId())
                .queryParam("client_secret", githubOAuthProperties.getSecret())
                .queryParam("code", code)
                .build().toUriString();
    }

    public String makeUserInfoRequestUrl() {
        return UriComponentsBuilder.newInstance()
                .scheme("https")
                .host("api.github.com")
                .path("user")
                .build().toUriString();
    }

    public String getAccessToken(String url) {
        GithubOAuthToken githubOAuthToken = null;
        try {
            ResponseEntity<String> token = oauthAdaptor.getToken(url);
            githubOAuthToken = objectMapper.readValue(token.getBody(), GithubOAuthToken.class);
            log.info("{}",githubOAuthToken);
        } catch (JsonProcessingException e) {
            log.error("{}",e);
        }

        return githubOAuthToken.getAccessToken();
    }

    public String getUserInfo(String url, String accessToken) {
        return oauthAdaptor.getUser(url, accessToken).getBody();
    }



}
