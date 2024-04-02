package live.databo3.front.member.adaptor;

import live.databo3.front.member.dto.MemberRequestDto;
import live.databo3.front.member.dto.ResponseDto;
import live.databo3.front.member.dto.ResponseHeaderDto;
import live.databo3.front.member.dto.TokenResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public interface MemberAdaptor {
    ResponseEntity<ResponseDto<ResponseHeaderDto, TokenResponseDto>> doLogin(MemberRequestDto memberRequestDto);

    void doRegister(MemberRequestDto memberRequestDto);
}
