package com.front.member.adaptor.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.front.member.adaptor.MemberAdaptor;
import com.front.member.dto.MemberRequestDto;
import com.front.member.dto.ResponseDto;
import com.front.member.dto.ResponseHeaderDto;
import com.front.member.dto.TokenResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;


@Slf4j
@Component
@RequiredArgsConstructor
public class MemberAdaptorImpl implements MemberAdaptor {

    private final RestTemplate restTemplate;

    @Value("${gateway.api.url}")
    String gatewayDomain;

    @Override
    public ResponseEntity<ResponseDto> doLogin(MemberRequestDto memberRequestDto) {



        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<MemberRequestDto> request = new HttpEntity<>(memberRequestDto, httpHeaders);

//
//        ResponseEntity<ResponseDto> exchange = restTemplate.exchange(
//                "http://localhost:9090/auth/login",
//                HttpMethod.POST,
//                entity,
//                ResponseDto.class
//        );




        ResponseEntity<ResponseDto> result = restTemplate.postForEntity(gatewayDomain+"/auth/login", request, ResponseDto.class);

        log.info("{}",result.getBody());
        TokenResponseDto tokenResponseDto = new ObjectMapper().convertValue(result.getBody().getBody(), TokenResponseDto.class);
        log.info("{}", tokenResponseDto);
        ResponseHeaderDto responseHeaderDto = new ObjectMapper().convertValue(result.getBody().getHeader(), ResponseHeaderDto.class);
        log.info("{}", responseHeaderDto);
        return result;
    }

    @Override
    public void doRegister(MemberRequestDto memberRequestDto) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<MemberRequestDto> request = new HttpEntity<>(memberRequestDto, headers);
        restTemplate.postForEntity(gatewayDomain+"/register", request, Void.class);
    }
}
