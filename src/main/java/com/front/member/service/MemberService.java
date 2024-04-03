package com.front.member.service;

import com.front.member.dto.MemberRequestDto;
import com.front.member.dto.ResponseDto;
import com.front.member.dto.ResponseHeaderDto;
import com.front.member.dto.TokenResponseDto;

import java.util.Optional;

public interface MemberService {
    Optional<ResponseDto<ResponseHeaderDto, TokenResponseDto>> doLogin(MemberRequestDto memberRequestDto);

    void doRegister(MemberRequestDto memberRequestDto);
}
