package live.databo3.front.adaptor.impl;

import live.databo3.front.adaptor.ElectChargeAdaptor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ElectChargeAdaptorImpl implements ElectChargeAdaptor {
    private final RestTemplate restTemplate;
    @Value("${gateway.api.url}")
    String gatewayDomain;

    @Override
    public long getElectCharge(String organizationName) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<String> request = new HttpEntity<>(httpHeaders);
        ResponseEntity<Long> exchange = restTemplate.exchange(
                gatewayDomain + "/api/ruleengine/{organizationId}/electchage",
                HttpMethod.GET,
                request,
                new ParameterizedTypeReference<>() {
                },organizationName);
        return exchange.getBody();
    }
}
