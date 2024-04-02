package com.front.oauth.adaptor.impl;

import com.front.oauth.adaptor.OAuthAdaptor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OAuthAdaptorImpl implements OAuthAdaptor {
    private final RestTemplate restTemplate;

    @Override
    public ResponseEntity<String> getToken(String url) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        return restTemplate.exchange(
                url,
                HttpMethod.POST,
                new HttpEntity<>(headers),
                String.class
        );
    }

    @Override
    public ResponseEntity<String> getUser(String url, String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION,"Bearer "+accessToken);
        return restTemplate.exchange(
                url,
                HttpMethod.GET,
                new HttpEntity<>(headers),
                String.class
        );
    }

    @Override
    public ResponseEntity<String> getUser(String url,String clientId, String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("client_id",clientId);
        headers.add("access_token",accessToken);

        return restTemplate.exchange(
                url,
                HttpMethod.POST,
                new HttpEntity<>(headers),
                String.class
        );
    }
}
