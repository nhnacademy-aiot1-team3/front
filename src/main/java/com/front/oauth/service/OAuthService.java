package com.front.oauth.service;

import com.front.member.dto.ResponseDto;
import com.front.member.dto.ResponseHeaderDto;
import com.front.member.dto.TokenResponseDto;

/**
 * OAuthService
 *
 * @author 양현성
 */
public interface OAuthService {

    String makeLoginUrl();

    ResponseDto<ResponseHeaderDto, TokenResponseDto> doOAuthLogin(String domain,String code);

}
