package com.front.member.adaptor.impl;

import com.front.member.adaptor.MemberAdaptor;
import com.front.member.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * RestTempalte을 사용해서 gateway와 통신하여 로그인, 회원가입을 하는 클래스
 *
 * @author 이지현
 * @version 1.0.0
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class MemberAdaptorImpl implements MemberAdaptor {

    private final RestTemplate restTemplate;

    @Value("${gateway.api.url}")
    String gatewayDomain;

    /**
     * 로그인 요청을 id, password를 담아서 게이트웨이에 요청을 보내고 성공 실패에 대한 결과를 받음
     *
     * @param memberRequestDto 로그인에 필요한 id, password가 담긴 dto
     * @return header과 body로 구성되어있는 ResponseDto입니다
     *         header은 resultCode,resultMessage로 구성되어있는 ResponseHeaderDto이고
     *         body는 accessToken과 refreshToken과 accessToken의 유효기간 refreshToken의 유효기간이 담겨있는 TokenResponseDto.
     * @since 1.0.0
     */
    @Override
    public ResponseEntity<ResponseDto<ResponseHeaderDto, TokenResponseDto>> doLogin(MemberRequestDto memberRequestDto) {

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<MemberRequestDto> request = new HttpEntity<>(memberRequestDto, httpHeaders);

        ResponseEntity<ResponseDto<ResponseHeaderDto, TokenResponseDto>> exchange = restTemplate.exchange(
                gatewayDomain+"/auth/login",
                HttpMethod.POST,
                request,
                new ParameterizedTypeReference<>() {
                }
        );
        ResponseEntity.
        log.info("{}",exchange.getBody());
        log.info("{}",exchange.getBody().getBody());
        log.info("{}",exchange.getBody().getHeader());

//        ResponseEntity<ResponseDto<ResponseHeaderDto, TokenResponseDto>> result = restTemplate.postForEntity(
//                gatewayDomain+"/auth/login",
//                request,
//                ResponseDto.class);

//        log.info("{}",result.getBody());
//        TokenResponseDto tokenResponseDto = new ObjectMapper().convertValue(result.getBody().getBody(), TokenResponseDto.class);
//        log.info("{}", tokenResponseDto);
//        ResponseHeaderDto responseHeaderDto = new ObjectMapper().convertValue(result.getBody().getHeader(), ResponseHeaderDto.class);
//        log.info("{}", responseHeaderDto);
        return exchange;
    }

    /**
     * 회원가입 요청을 id, password를 담아서 게이트웨이에 회원가입 요청을 보냅니다.
     *
     * @param memberRegisterRequest 회원가입에 필요에 필요한 id, password를 받는 dto (이건 수정할겁니다)
     * @since 1.0.0
     */
    @Override
    public void doRegister(MemberRegisterRequest memberRegisterRequest) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<MemberRegisterRequest> request = new HttpEntity<>(memberRegisterRequest, headers);
        restTemplate.postForEntity(gatewayDomain+"/api/account/member/register", request, Void.class);
    }
}
