package live.databo3.front.oauth.adaptor;

import live.databo3.front.member.dto.ResponseDto;
import live.databo3.front.member.dto.ResponseHeaderDto;
import live.databo3.front.member.dto.TokenResponseDto;
import org.springframework.http.ResponseEntity;

/**
 * databo3 인증서버로 code를 전달하는 클래스
 *
 * @author 양현성
 */
public interface OAuthAdaptor {
    ResponseEntity<ResponseDto<ResponseHeaderDto, TokenResponseDto>> doOAuthLogin(String domain, String code);
}
