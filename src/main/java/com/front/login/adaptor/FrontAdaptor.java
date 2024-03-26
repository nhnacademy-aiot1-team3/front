package com.front.login.adaptor;

import com.front.login.dto.UserDto;
import org.springframework.stereotype.Component;

@Component
public interface FrontAdaptor {
    void doLogin(UserDto userDto);
}
