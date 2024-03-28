package com.front.member.service;

import com.front.member.dto.MemberRequestDto;

import java.util.Optional;

public interface MemberService {
    Optional<String> doLogin(MemberRequestDto memberRequestDto);

    void doRegister(MemberRequestDto memberRequestDto);
}