package com.front.member.service;

import com.front.member.dto.MemberRequestDto;

public interface MemberService {
    void doLogin(MemberRequestDto userDto);
}
