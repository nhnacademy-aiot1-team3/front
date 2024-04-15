package live.databo3.front.member;

import com.front.member.controller.MemberController;
import com.front.member.dto.ResponseDto;
import com.front.member.dto.ResponseHeaderDto;
import com.front.member.dto.TokenResponseDto;
import com.front.member.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import javax.servlet.http.Cookie;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MemberController.class)
class MemberControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MemberService memberService;

    @Test
    @DisplayName("로그인 성공")
    void loginSuccessTest() throws Exception {
        String member = "{\"id\":\"admin\", \"pw\":\"123\"}";
        ResponseHeaderDto header = new ResponseHeaderDto();
        TokenResponseDto body = new TokenResponseDto();
        ResponseDto<ResponseHeaderDto, TokenResponseDto> result = new ResponseDto<ResponseHeaderDto, TokenResponseDto>(header, body);

        given(memberService.doLogin(any())).willReturn(Optional.of(result));

        mockMvc.perform(post("/login")
                        .contentType(MediaType.TEXT_HTML)
                        .content(member))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(model().attribute("message","로그인 성공"))
                .andExpect(model().attribute("searchUrl","/"))
                .andExpect(view().name("alert"))
                .andDo(print());
    }

    @Test
    @DisplayName("로그인 실패")
    void loginFailTest() throws Exception {
        String requestJson = "{\"id\":\"admin\", \"pw\": \"123\"}";
        ResponseDto<ResponseHeaderDto, TokenResponseDto> result = null;
        Optional<ResponseDto<ResponseHeaderDto, TokenResponseDto>> optional = Optional.ofNullable(result);
        given(memberService.doLogin(any())).willReturn(optional);

        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(model().attribute("message","로그인 실패"))
                .andExpect(model().attribute("searchUrl","login"))
                .andExpect(view().name("alert"))
                .andDo(print());
    }

    @Test
    @DisplayName("로그 아웃")
    void logoutSuccessTest() throws Exception {

        Cookie cookie = new Cookie("token", "dafadfadew23fwedf");
        Cookie cookie2 = new Cookie("asdf", "dafadfadew23fwedf");
//
        mockMvc.perform(get("/logout").cookie(cookie,cookie2))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"))
                .andExpect(cookie().maxAge("token", 0))
                .andDo(print());
        ;
    }
}