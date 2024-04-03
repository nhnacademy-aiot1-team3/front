package com.front.member.service;

import com.front.member.dto.*;

import java.util.Optional;

public interface MemberService {
    Optional<ResponseDto<ResponseHeaderDto, TokenResponseDto>> doLogin(MemberRequestDto memberRequestDto);

    void doRegister(MemberRegisterRequest memberRegisterRequest);
}
