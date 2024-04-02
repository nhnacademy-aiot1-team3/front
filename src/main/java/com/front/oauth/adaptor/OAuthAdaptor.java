package com.front.oauth.adaptor;

import org.springframework.http.ResponseEntity;

public interface OAuthAdaptor {

    ResponseEntity<String> getToken(String url);

    ResponseEntity<String> getUser(String url, String token);

    ResponseEntity<String> getUser(String url, String clientId, String accessToken);
}
