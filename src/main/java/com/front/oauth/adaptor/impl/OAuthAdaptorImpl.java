package com.front.oauth.adaptor.impl;

import com.front.member.dto.ResponseDto;
import com.front.member.dto.ResponseHeaderDto;
import com.front.member.dto.TokenResponseDto;
import com.front.oauth.adaptor.OAuthAdaptor;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.List;


@Component
@RequiredArgsConstructor
public class OAuthAdaptorImpl implements OAuthAdaptor {
    private final RestTemplate restTemplate;
    @Override
    public ResponseEntity<ResponseDto<ResponseHeaderDto, TokenResponseDto>> doOAuthLogin(String domain,String code) {
        return restTemplate.exchange(
                "http://localhost:9090/auth/{domain}/oauth",
                HttpMethod.POST,
                makeHttpEntity(code),
                new ParameterizedTypeReference<>() {
                },
                domain
        );
    }

    private HttpEntity<MultiValueMap<String, String>> makeHttpEntity(String code) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("code", code);
        HttpHeaders headers = new HttpHeaders();
        return new HttpEntity<>(params,headers);
    }
}
