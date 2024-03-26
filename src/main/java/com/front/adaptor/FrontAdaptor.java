package com.front.adaptor;

import com.front.dto.UserDto;
import org.springframework.stereotype.Component;

@Component
public interface FrontAdaptor {
    void doLogin(UserDto userDto);
}
