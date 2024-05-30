package live.databo3.front.adaptor.impl;

import live.databo3.front.adaptor.MemberAdaptor;
import live.databo3.front.dto.MemberDto;
import live.databo3.front.dto.request.MemberModifyStateRequest;
import live.databo3.front.dto.request.UpdatePasswordRequest;
import live.databo3.front.member.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class MemberAdaptorImpl implements MemberAdaptor {

    private final String MEMBER_URL = "/api/account/member";

    private final RestTemplate restTemplate;

    @Value("${gateway.api.url}")
    String gatewayDomain;

    /**
     * 로그인 요청을 id, password를 담아서 게이트웨이에 요청을 보내고 성공 실패에 대한 결과를 받음
     *
     * @param memberRequestDto 로그인에 필요한 id, password가 담긴 dto
     * @return header과 body로 구성되어있는 ResponseDto입니다
     * header은 resultCode,resultMessage로 구성되어있는 ResponseHeaderDto이고
     * body는 accessToken과 refreshToken과 accessToken의 유효기간 refreshToken의 유효기간이 담겨있는 TokenResponseDto.
     * @since 1.0.0
     */
    @Override
    public ResponseDto<ResponseHeaderDto, TokenResponseDto> doLogin(MemberRequestDto memberRequestDto) {

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<MemberRequestDto> request = new HttpEntity<>(memberRequestDto, httpHeaders);
        ResponseEntity<ResponseDto<ResponseHeaderDto,TokenResponseDto>> exchange = restTemplate.exchange(
                gatewayDomain+"/auth/login",
                HttpMethod.POST,
                request,
                new ParameterizedTypeReference<>() {
                }
        );

        return exchange.getBody();
    }

    /**
     * 회원가입 요청을 id, password, email를 담아서 게이트웨이에 회원가입 요청을 보냅니다.
     *
     * @param memberRegisterRequest 회원가입에 필요에 필요한 정보를(id, password, email) 받는 request
     * @since 1.0.0
     */
    @Override
    public String doRegister(MemberRegisterRequest memberRegisterRequest) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<MemberRegisterRequest> request = new HttpEntity<>(memberRegisterRequest, httpHeaders);

        return restTemplate.exchange(
                gatewayDomain+"/api/account/member/register",
                HttpMethod.POST,
                request,
                String.class
        ).getBody();
    }

    @Override
    public List<MemberDto> getMembers() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<String> request = new HttpEntity<>(httpHeaders);
        ResponseEntity<List<MemberDto>> exchange = restTemplate.exchange(
                gatewayDomain + MEMBER_URL + "/list",
                HttpMethod.GET,
                request,
                new ParameterizedTypeReference<>() {
                });
        return exchange.getBody();
    }

    @Override
    public List<MemberDto> getMembersByRoleAndState(int roleId, int stateId) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<String> request = new HttpEntity<>(httpHeaders);
        ResponseEntity<List<MemberDto>> exchange = restTemplate.exchange(
                gatewayDomain + MEMBER_URL + "/list?roleId={roleId}&stateId={stateID}",
                HttpMethod.GET,
                request,
                new ParameterizedTypeReference<>() {
                }, roleId, stateId);
        return exchange.getBody();
    }

    @Override
    public void modifyPassword(String memberId, UpdatePasswordRequest updatePasswordRequest) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));


        HttpEntity<UpdatePasswordRequest> request = new HttpEntity<>(updatePasswordRequest, httpHeaders);
        ResponseEntity<Object> exchange = restTemplate.exchange(
                gatewayDomain + MEMBER_URL + "/modify/{memberId}",
                HttpMethod.PUT,
                request,
                new ParameterizedTypeReference<>() {
                },memberId
        );
        exchange.getBody();
    }

    @Override
    public void modifyMemberState(MemberModifyStateRequest memberModifyStateRequest) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<MemberModifyStateRequest> request = new HttpEntity<>(memberModifyStateRequest, httpHeaders);
        ResponseEntity<Object> exchange = restTemplate.exchange(
                gatewayDomain + MEMBER_URL + "/modify/state",
                HttpMethod.PUT,
                request,
                new ParameterizedTypeReference<>() {
                }
        );
        exchange.getBody();
    }

}
