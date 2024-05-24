package live.databo3.front.adaptor.impl;

import live.databo3.front.adaptor.ErrorLogAdaptor;
import live.databo3.front.member.dto.MemberRequestDto;
import live.databo3.front.owner.dto.ErrorLogResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ErrorLogAdaptorImpl implements ErrorLogAdaptor {
    private final RestTemplate restTemplate;
    @Value("${gateway.api.url}")
    String gatewayDomain;

    @Override
    public List<ErrorLogResponseDto> getErrorLog(Integer organizationId) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<MemberRequestDto> request = new HttpEntity<>(httpHeaders);
        ResponseEntity<List<ErrorLogResponseDto>> exchange = restTemplate.exchange(
                gatewayDomain+"api/sensor/error/log/org/{organizationId}",
                HttpMethod.GET,
                request,
                new ParameterizedTypeReference<>() {
                }, organizationId
        );

        return exchange.getBody();
    }
}
