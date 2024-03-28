package com.front.member.adaptor;

import com.front.member.dto.MemberRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public interface MemberAdaptor {
    ResponseEntity<Void> doLogin(MemberRequestDto memberRequestDto);

    void doRegister(MemberRequestDto memberRequestDto);
}
