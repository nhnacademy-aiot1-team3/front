package live.databo3.front.member.service.impl;

import live.databo3.front.member.adaptor.MemberAdaptor;
import live.databo3.front.member.dto.*;
import live.databo3.front.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * 사용자 관련 작업 수행하는 service
 * @author 이지현
 * @version 1.0.0
 */
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
//    private final MemberAdaptor memberAdaptor;
//
//    /**
//     * 로그인 요청 정보 바탕으로 인증 절차 진행, 인증 결과에 따른 토큰 정보 반환
//     * @param memberRequestDto 사용자 가입 정보 (id, pw)
//     * @since 1.0.0
//     * @return 로그인 성공시 토큰 정보를 Optional에 담아서 return, 로그인 실패시 빈 객체를 Optional에 담아서 return
//     */
//    @Override
//    public ResponseDto<ResponseHeaderDto, TokenResponseDto> doLogin(MemberRequestDto memberRequestDto) {
//        ResponseDto<ResponseHeaderDto, TokenResponseDto> answer = memberAdaptor.doLogin(memberRequestDto);
//        return answer;
//    }
//
//    /**
//     * 회원 가입 정보를 바탕으로 회원 가입 절차 진행
//     * @param memberRegisterRequest 사용자 가입 정보 (id, pw, email)
//     * @since 1.0.0
//     */
//    @Override
//    public String doRegister(MemberRegisterRequest memberRegisterRequest) {
//        return memberAdaptor.doRegister(memberRegisterRequest);
//    }
//
//    @Override
//    public boolean doIdCheck(String id) {
//        return memberAdaptor.doIdCheck(id);
//    }
//
//    @Override
//    public String postEmailSend(EmailRequest emailRequest) {
//        return memberAdaptor.postEmailSend(emailRequest);
//    }
//
//    @Override
//    public String postEmailVerify(CodeEmailRequest codeEmailRequest) {
//        return memberAdaptor.postEmailVerify(codeEmailRequest);
//    }
}
