package com.front.service.impl;

import com.front.adaptor.FrontAdaptor;
import com.front.dto.UserDto;
import com.front.service.FrontService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class FrontServiceImpl implements FrontService {
    private final FrontAdaptor frontAdaptor;
    @Override
    public void doLogin(UserDto userDto) {
        frontAdaptor.doLogin(userDto);
    }
}
