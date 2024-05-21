package live.databo3.front.adaptor;

import live.databo3.front.member.dto.ResponseDto;
import live.databo3.front.member.dto.ResponseHeaderDto;
import live.databo3.front.member.dto.TokenResponseDto;
import org.springframework.http.ResponseEntity;

public interface AuthAdaptor {
    ResponseEntity<ResponseDto<ResponseHeaderDto, TokenResponseDto>> tokenReIssue(String refreshToken);
}
