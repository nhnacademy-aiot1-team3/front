package com.front.oauth.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.front.oauth.adaptor.OAuthAdaptor;
import com.front.oauth.dto.GithubOAuthToken;
import com.front.oauth.dto.OAuthToken;
import com.front.oauth.properties.GithubOAuthProperties;
import com.front.oauth.properties.PaycoOAuthProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaycoOAuthService implements OAuthService{
    private final PaycoOAuthProperties paycoOAuthProperties;
    private final ObjectMapper objectMapper;
    private final OAuthAdaptor oauthAdaptor;

    @Override
    public String makeLoginUrl() {
        return UriComponentsBuilder.newInstance()
                .scheme("https")
                .host("id.payco.com")
                .path("oauth2.0/authorize")
                .queryParam("response_type","code")
                .queryParam("client_id",paycoOAuthProperties.getClientId())
                .queryParam("redirect_uri",paycoOAuthProperties.getRedirectUrl())
                .queryParam("serviceProviderCode","FRIENDS")
                .queryParam("userLocale","ko_KR")
                .build().toUriString();
    }

    @Override
    public String makeTokenRequestUrl(String code) {
        return UriComponentsBuilder.newInstance()
                .scheme("https")
                .host("id.payco.com")
                .path("oauth2.0/token")
                .queryParam("grant_type","authorization_code")
                .queryParam("client_id",paycoOAuthProperties.getClientId())
                .queryParam("client_secret",paycoOAuthProperties.getSecret())
                .queryParam("code",code)
                .build().toUriString();
    }

    @Override
    public String makeUserInfoRequestUrl() {
        return UriComponentsBuilder.newInstance()
                .scheme("https")
                .host("apis-payco.krp.toastoven.net")
                .path("payco/friends/find_member_v2.json")
                .build().toUriString();
    }

    @Override
    public String getAccessToken(String url) {
        OAuthToken oAuthToken = null;
        try {
            ResponseEntity<String> token = oauthAdaptor.getToken(url);
            oAuthToken = objectMapper.readValue(token.getBody(), OAuthToken.class);
            log.info("{}",oAuthToken);
        } catch (JsonProcessingException e) {
            log.error("{}",e);
        }

        return oAuthToken.getAccessToken();
    }

    @Override
    public String getUserInfo(String url, String accessToken) {
        return oauthAdaptor.getUser(url, paycoOAuthProperties.getClientId() ,accessToken).getBody();
    }
}
