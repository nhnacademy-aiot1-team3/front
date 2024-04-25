package live.databo3.front.oauth.adaptor.impl;

import live.databo3.front.member.dto.ResponseDto;
import live.databo3.front.member.dto.ResponseHeaderDto;
import live.databo3.front.member.dto.TokenResponseDto;
import live.databo3.front.oauth.adaptor.OAuthAdaptor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;


@Component
@RequiredArgsConstructor
public class OAuthAdaptorImpl implements OAuthAdaptor {
    private final RestTemplate restTemplate;
    @Value("${gateway.api.url}")
    String gatewayDomain;
    @Override
    public ResponseEntity<ResponseDto<ResponseHeaderDto, TokenResponseDto>> doOAuthLogin(String domain, String code) {
        return restTemplate.exchange(
                "http://localhost:9090/auth/{domain}/oauth",
                HttpMethod.POST,
                makeHttpEntity(code),
                new ParameterizedTypeReference<>() {
                },
                domain
        );
    }
    private HttpEntity<MultiValueMap<String, String>> makeHttpEntity(String code) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("code", code);
        HttpHeaders headers = new HttpHeaders();
        return new HttpEntity<>(params,headers);
    }
}
