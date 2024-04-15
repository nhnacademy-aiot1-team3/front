package com.front.auth.adaptor;

import com.front.member.dto.ResponseDto;
import com.front.member.dto.ResponseHeaderDto;
import com.front.member.dto.TokenResponseDto;
import org.springframework.http.ResponseEntity;

public interface AuthAdaptor {
    ResponseEntity<ResponseDto<ResponseHeaderDto, TokenResponseDto>> tokenReIssue(String refreshToken);
}
