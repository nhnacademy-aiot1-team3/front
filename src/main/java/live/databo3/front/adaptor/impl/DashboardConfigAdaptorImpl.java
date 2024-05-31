package live.databo3.front.adaptor.impl;

import live.databo3.front.adaptor.DashboardConfigAdaptor;
import live.databo3.front.dto.DashboardConfigDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DashboardConfigAdaptorImpl implements DashboardConfigAdaptor {

    private final RestTemplate restTemplate;

    @Value("${gateway.api.url}")
    String gatewayDomain;

    @Override
    public List<DashboardConfigDto> dashboardConfigDtoList() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<String> request = new HttpEntity<>(httpHeaders);
        ResponseEntity<List<DashboardConfigDto>> exchange = restTemplate.exchange(
                gatewayDomain + "/api/account/config/dashboard",
                HttpMethod.GET,
                request,
                new ParameterizedTypeReference<>() {
                });
        return exchange.getBody();
    }
}
