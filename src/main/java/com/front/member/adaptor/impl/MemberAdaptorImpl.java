package com.front.member.adaptor.impl;

import com.front.member.adaptor.MemberAdaptor;
import com.front.member.dto.MemberRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;


@Component
@RequiredArgsConstructor
public class MemberAdaptorImpl implements MemberAdaptor {

    private final RestTemplate restTemplate;

    @Value("${gateway.api.url}")
    String gatewayDomain;

    @Override
    public ResponseEntity<MemberRequestDto> doLogin(MemberRequestDto memberRequestDto) {
        ResponseEntity<MemberRequestDto> a = null;
try {
    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.setContentType(MediaType.APPLICATION_JSON);
    httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));

    HttpEntity<MemberRequestDto> request = new HttpEntity<>(memberRequestDto, httpHeaders);
    a = restTemplate.postForEntity(gatewayDomain + "/auth/login", request, MemberRequestDto.class);
} catch (
        RestClientException e) {
    e.getMessage();
}
        return  a;
    }

    @Override
    public void doRegister(MemberRequestDto memberRequestDto) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<MemberRequestDto> request = new HttpEntity<>(memberRequestDto);
        restTemplate.postForEntity(gatewayDomain+"/register", request, Void.class);
    }
}
