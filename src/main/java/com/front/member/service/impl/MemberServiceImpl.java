package com.front.member.service.impl;

import com.front.member.adaptor.MemberAdaptor;
import com.front.member.dto.*;
import com.front.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberAdaptor memberAdaptor;
    @Override
    public Optional<ResponseDto<ResponseHeaderDto,TokenResponseDto>> doLogin(MemberRequestDto memberRequestDto) {
        ResponseEntity<ResponseDto<ResponseHeaderDto, TokenResponseDto>> answer = memberAdaptor.doLogin(memberRequestDto);
//        HttpHeaders header = answer.getHeaders();
//        header.get("Authorization").stream().findFirst();
        return Optional.ofNullable(answer.getBody());
    }

    @Override
    public void doRegister(MemberRegisterRequest memberRegisterRequest) {
        memberAdaptor.doRegister(memberRegisterRequest);
    }
}
