package com.front.oauth.adaptor;

import com.front.member.dto.ResponseDto;
import com.front.member.dto.ResponseHeaderDto;
import com.front.member.dto.TokenResponseDto;
import org.springframework.http.ResponseEntity;

/**
 * databo3 인증서버로 code를 전달하는 클래스
 *
 * @author 양현성
 */
public interface OAuthAdaptor {
    ResponseEntity<ResponseDto<ResponseHeaderDto, TokenResponseDto>> doOAuthLogin(String domain,String code);
}
