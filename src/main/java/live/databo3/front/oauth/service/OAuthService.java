package live.databo3.front.oauth.service;

import live.databo3.front.member.dto.ResponseDto;
import live.databo3.front.member.dto.ResponseHeaderDto;
import live.databo3.front.member.dto.TokenResponseDto;

/**
 * OAuthService
 *
 * @author 양현성
 */
public interface OAuthService {

    String makeLoginUrl();

    ResponseDto<ResponseHeaderDto, TokenResponseDto> doOAuthLogin(String domain,String code);

}
