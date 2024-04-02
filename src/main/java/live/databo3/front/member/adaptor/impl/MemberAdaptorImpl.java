package live.databo3.front.member.adaptor.impl;

import live.databo3.front.member.adaptor.MemberAdaptor;
import live.databo3.front.member.dto.MemberRequestDto;
import live.databo3.front.member.dto.ResponseDto;
import live.databo3.front.member.dto.ResponseHeaderDto;
import live.databo3.front.member.dto.TokenResponseDto;
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

    private final RestTemplate restTemplate;

    @Value("${gateway.api.url}")
    String gatewayDomain;

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

    @Override
    public void doRegister(MemberRequestDto memberRequestDto) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<MemberRequestDto> request = new HttpEntity<>(memberRequestDto, headers);
        restTemplate.postForEntity(gatewayDomain+"/api/account/member", request, Void.class);
    }
}
