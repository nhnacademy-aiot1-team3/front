package live.databo3.front.member.adaptor;

import live.databo3.front.member.dto.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

/**
 *
 * @author 이지현
 * @version 1.0.0
 */
@Component
public interface MemberAdaptor {
    /**
     * resttemplate으로 로그인 정보 보내기 위한 메서드
     * @param memberRequestDto 로그인을 위한 정보 (id, password)
     * @return header과 body로 구성되어있는 ResponseDto입니다
     *         header은 resultCode,resultMessage로 구성되어있는 ResponseHeaderDto이고
     *         body는 accessToken과 refreshToken과 accessToken의 유효기간 refreshToken의 유효기간이 담겨있는 TokenResponseDto.
     * @since 1.0.0
     */
    ResponseDto<ResponseHeaderDto, TokenResponseDto> doLogin(MemberRequestDto memberRequestDto);

    /**
     * 회원가입 요청을 id, password, email를 담아서 게이트웨이에 회원가입 요청을 보냅니다.
     *
     * @param memberRegisterRequest 회원가입에 필요에 필요한 정보를(id, password, email) 받는 request
     * @since 1.0.0
     */
    String doRegister(MemberRegisterRequest memberRegisterRequest);

    boolean doIdCheck(String id);

    String postEmailSend(EmailRequest emailRequest);

    String postEmailVerify(CodeEmailRequest codeEmailRequest);
}
