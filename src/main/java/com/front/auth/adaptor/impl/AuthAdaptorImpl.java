package com.front.auth.adaptor.impl;

import com.front.auth.adaptor.AuthAdaptor;
import com.front.member.dto.ResponseDto;
import com.front.member.dto.ResponseHeaderDto;
import com.front.member.dto.TokenResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class AuthAdaptorImpl implements AuthAdaptor {

    private final RestTemplate restTemplate;
    @Override
    public ResponseEntity<ResponseDto<ResponseHeaderDto, TokenResponseDto>> tokenReIssue(String refreshToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + refreshToken);

        return restTemplate.exchange(
                "http://localhost:9090/auth/token/reissue",
                HttpMethod.POST,
                new HttpEntity<>(headers),
                new ParameterizedTypeReference<>() {
                }
        );
    }


}
