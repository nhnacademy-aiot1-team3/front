package com.front.login.adaptor.impl;

import com.front.login.adaptor.FrontAdaptor;
import com.front.login.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;


@Component
@RequiredArgsConstructor
public class FrontAdaptorImpl implements FrontAdaptor {

    private final RestTemplate restTemplate;

    @Override
    public void doLogin(UserDto userDto) {

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<UserDto> request = new HttpEntity<>(userDto);
        restTemplate.postForEntity("/login", request, Void.class);
    }
}
