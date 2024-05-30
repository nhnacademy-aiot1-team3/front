package live.databo3.front.adaptor.impl;

import live.databo3.front.adaptor.GeneralConfigAdaptor;
import live.databo3.front.admin.dto.GeneralConfigDto;
import live.databo3.front.admin.dto.request.GeneralConfigRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GeneralConfigAdaptorImpl implements GeneralConfigAdaptor {
    private final String GENERAL_CONFIG_URL = "/api/sensor/org";

    private final RestTemplate restTemplate;

    @Value("${gateway.api.url}")
    String gatewayDomain;

    @Override
    public GeneralConfigDto getGeneralConfig(int organizationId, String sensorSn, int sensorTypeId) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<String> request = new HttpEntity<>(httpHeaders);
        ResponseEntity<GeneralConfigDto> exchange = restTemplate.exchange(
                gatewayDomain + GENERAL_CONFIG_URL + "/{organizationId}/sensor/{sensorSn}/sensorType/{sensorTypeId}/general",
                HttpMethod.GET,
                request,
                new ParameterizedTypeReference<>() {
                },organizationId,sensorSn,sensorTypeId);
        return exchange.getBody();
    }

    @Override
    public GeneralConfigDto modifyGeneralConfig(GeneralConfigRequest generalConfigRequest, int organizationId, String sensorSn, int sensorTypeId) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<GeneralConfigRequest> request = new HttpEntity<>(generalConfigRequest, httpHeaders);
        ResponseEntity<GeneralConfigDto> exchange = restTemplate.exchange(
                gatewayDomain + GENERAL_CONFIG_URL + "/{organizationId}/sensor/{sensorSn}/sensorType/{sensorTypeId}/general",
                HttpMethod.PUT,
                request,
                new ParameterizedTypeReference<>() {
                },organizationId,sensorSn,sensorTypeId);
        return exchange.getBody();
    }
}
