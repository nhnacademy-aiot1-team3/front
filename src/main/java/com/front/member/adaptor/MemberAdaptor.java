package com.front.member.adaptor;

import com.front.member.dto.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

/**
 *
 */
@Component
public interface MemberAdaptor {
    ResponseEntity<ResponseDto<ResponseHeaderDto, TokenResponseDto>> doLogin(MemberRequestDto memberRequestDto);

    void doRegister(MemberRegisterRequest memberRegisterRequest);
}
