package com.front.oauth.service;

public interface OAuthService {
    String makeLoginUrl();

    String makeTokenRequestUrl(String code);

    String makeUserInfoRequestUrl();

    String getAccessToken(String url);

    String getUserInfo(String url, String accessToken);
}
