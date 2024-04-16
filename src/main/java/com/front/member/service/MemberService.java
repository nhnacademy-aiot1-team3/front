package com.front.member.service;

import com.front.member.dto.*;

import java.util.Optional;

/**
 * 사용자 관련 작업 수행하는 service
 * @author 이지현
 * @version 1.0.0
 */
public interface MemberService {
    /**
     * 로그인 요청 정보 바탕으로 인증 절차 진행, 인증 결과에 따른 토큰 정보 반환
     * @param memberRequestDto 사용자 가입 정보 (id, pw)
     * @return 로그인 성공시 토큰 정보를 Optional에 담아서 return, 로그인 실패시 빈 객체를 Optional에 담아서 return
     * @since 1.0.0
     */
    Optional<ResponseDto<ResponseHeaderDto, TokenResponseDto>> doLogin(MemberRequestDto memberRequestDto);

    /**
     * 회원 가입 정보를 바탕으로 회원 가입 절차 진행
     * @param memberRegisterRequest 사용자 가입 정보 (id, pw, email)
     * @since 1.0.0
     */
    void doRegister(MemberRegisterRequest memberRegisterRequest);

    boolean doIdCheck(String id);
}
