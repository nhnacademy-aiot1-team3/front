package com.front.login.service.impl;

import com.front.login.adaptor.FrontAdaptor;
import com.front.login.dto.LoginRequestDto;
import com.front.login.service.FrontService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class FrontServiceImpl implements FrontService {
    private final FrontAdaptor frontAdaptor;
    @Override
    public void doLogin(LoginRequestDto userDto) {
        frontAdaptor.doLogin(userDto);
    }
}
