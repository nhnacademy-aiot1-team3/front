package com.front.member.adaptor;

import com.front.member.dto.MemberRequestDto;
import org.springframework.stereotype.Component;

@Component
public interface MemberAdaptor {
    void doLogin(MemberRequestDto userDto);
}
