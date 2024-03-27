package com.front.login.adaptor;

import com.front.login.dto.LoginRequestDto;
import org.springframework.stereotype.Component;

@Component
public interface FrontAdaptor {
    void doLogin(LoginRequestDto userDto);
}
