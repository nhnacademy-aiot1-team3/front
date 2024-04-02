package live.databo3.front.member.service;

import live.databo3.front.member.dto.MemberRequestDto;
import live.databo3.front.member.dto.ResponseDto;
import live.databo3.front.member.dto.ResponseHeaderDto;
import live.databo3.front.member.dto.TokenResponseDto;

import java.util.Optional;

public interface MemberService {
    Optional<ResponseDto<ResponseHeaderDto, TokenResponseDto>> doLogin(MemberRequestDto memberRequestDto);

    void doRegister(MemberRequestDto memberRequestDto);
}
