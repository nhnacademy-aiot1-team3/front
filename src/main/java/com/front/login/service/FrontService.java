package com.front.login.service;

import com.front.login.dto.LoginRequestDto;

public interface FrontService {
    void doLogin(LoginRequestDto userDto);
}
