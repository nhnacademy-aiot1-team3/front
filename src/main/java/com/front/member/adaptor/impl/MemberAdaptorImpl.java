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
import org.springframework.web.client.RestTemplate;

import java.util.List;


@Component
@RequiredArgsConstructor
public class MemberAdaptorImpl implements MemberAdaptor {

    private final RestTemplate restTemplate;

    @Value("${account.api.url}")
    String gatewayDomain;

    @Override
    public ResponseEntity<Void> doLogin(MemberRequestDto memberRequestDto) {

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<MemberRequestDto> request = new HttpEntity<>(memberRequestDto);
        return restTemplate.postForEntity(gatewayDomain+"/login", request, Void.class);

    }
}
