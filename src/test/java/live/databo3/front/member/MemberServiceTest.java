package live.databo3.front.member;

import com.front.member.adaptor.MemberAdaptor;
import com.front.member.dto.*;
import com.front.member.service.impl.MemberServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.Objects;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class MemberServiceTest {
    @InjectMocks
    MemberServiceImpl memberService;

    @Mock
    MemberAdaptor memberAdaptor;

    @Test
    @DisplayName("로그인 테스트")
    void doLoginTest(){
        MemberRequestDto request = new MemberRequestDto("admin", "123");
        ResponseHeaderDto header = new ResponseHeaderDto();
        TokenResponseDto body = new TokenResponseDto();
        ResponseDto<ResponseHeaderDto, TokenResponseDto> answer = new ResponseDto<>(header, body);

        when(memberAdaptor.doLogin(any())).thenReturn(ResponseEntity.ok(answer));
        Optional<ResponseDto<ResponseHeaderDto, TokenResponseDto>> result = memberService.doLogin(request);
        Assertions.assertTrue(result.isPresent());
        Assertions.assertTrue(Objects.nonNull(result.get().getHeader()));
        Assertions.assertTrue(Objects.nonNull(result.get().getBody()));
    }

    @Test
    @DisplayName("회원가입 테스트")
    void doRegisterTest(){
        MemberRegisterRequest request = new MemberRegisterRequest("admin","123","");
        memberService.doRegister(request);

        verify(memberAdaptor,times(1)).doRegister(any());
    }
}