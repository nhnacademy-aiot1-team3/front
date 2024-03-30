package com.front.member.adaptor;

import com.front.member.dto.MemberRequestDto;
import com.front.member.dto.ResponseDto;
import com.front.member.dto.ResponseHeaderDto;
import com.front.member.dto.TokenResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public interface MemberAdaptor {
    ResponseEntity<ResponseDto<ResponseHeaderDto, TokenResponseDto>> doLogin(MemberRequestDto memberRequestDto);

    void doRegister(MemberRequestDto memberRequestDto);
}
