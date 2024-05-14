package live.databo3.front.auth.adaptor.impl;

import live.databo3.front.auth.adaptor.AuthAdaptor;
import live.databo3.front.member.dto.ResponseDto;
import live.databo3.front.member.dto.ResponseHeaderDto;
import live.databo3.front.member.dto.TokenResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class AuthAdaptorImpl implements AuthAdaptor {

    private final RestTemplate restTemplate;
    @Value("${gateway.api.url}")
    String gatewayDomain;

    @Override
    public ResponseEntity<ResponseDto<ResponseHeaderDto, TokenResponseDto>> tokenReIssue(String refreshToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, refreshToken);

        return restTemplate.exchange(
                gatewayDomain+"/auth/token/reissue",
                HttpMethod.POST,
                new HttpEntity<>(headers),
                new ParameterizedTypeReference<>() {
                }
        );
    }


}
