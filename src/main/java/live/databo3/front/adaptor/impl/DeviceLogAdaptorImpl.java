package live.databo3.front.adaptor.impl;

import live.databo3.front.adaptor.DeviceLogAdaptor;
import live.databo3.front.owner.dto.DeviceLogResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DeviceLogAdaptorImpl implements DeviceLogAdaptor {
    private final RestTemplate restTemplate;
    @Value("${gateway.api.url}")
    String gatewayDomain;

    @Override
    public List<DeviceLogResponseDto> getDeviceLog(Integer organizationId) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<String> request = new HttpEntity<>(httpHeaders);
        ResponseEntity<List<DeviceLogResponseDto>> exchange = restTemplate.exchange(
                gatewayDomain+"/api/sensor/device/log/org/{organizationId}",
                HttpMethod.GET,
                request,
                new ParameterizedTypeReference<>() {
                }, organizationId
        );

        return exchange.getBody();
    }
}
