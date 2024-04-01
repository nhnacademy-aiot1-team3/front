package com.front.member.service.impl;

import com.front.member.adaptor.MemberAdaptor;
import com.front.member.dto.MemberRequestDto;
import com.front.member.dto.ResponseDto;
import com.front.member.dto.ResponseHeaderDto;
import com.front.member.dto.TokenResponseDto;
import com.front.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * 사용자 관련 작업 수행하는 service
 * @author 이지현
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberAdaptor memberAdaptor;

    /**
     * 로그인 요청 정보 바탕으로 인증 절차 진행, 인증 결과에 따른 토큰 정보 반환
     * @param memberRequestDto 사용자 가입 정보 (id, pw)
     * @return 로그인 성공시 토큰 정보를 Optional에 담아서 return, 로그인 실패시 빈 객체를 Optional에 담아서 return
     */
    @Override
    public Optional<ResponseDto<ResponseHeaderDto,TokenResponseDto>> doLogin(MemberRequestDto memberRequestDto) {
        ResponseEntity<ResponseDto<ResponseHeaderDto, TokenResponseDto>> answer = memberAdaptor.doLogin(memberRequestDto);
//        HttpHeaders header = answer.getHeaders();
//        header.get("Authorization").stream().findFirst();
        return Optional.ofNullable(answer.getBody());
    }

    /**
     * 회원 가입 정보를 바탕으로 회원 가입 절차 진행
     * @param memberRequestDto 사용자 가입 정보 (id, pw)
     */
    @Override
    public void doRegister(MemberRequestDto memberRequestDto) {
        memberAdaptor.doRegister(memberRequestDto);
    }
}
