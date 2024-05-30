package live.databo3.front.adaptor.impl;

import live.databo3.front.adaptor.ValueConfigAdaptor;
import live.databo3.front.dto.ConfigsDto;
import live.databo3.front.dto.ValueConfigDto;
import live.databo3.front.dto.request.ValueConfigRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ValueConfigAdaptorImpl implements ValueConfigAdaptor {
    private final String VALUE_CONFIG_URL = "/api/sensor/org";

    private final RestTemplate restTemplate;

    @Value("${gateway.api.url}")
    String gatewayDomain;

    @Override
    public ConfigsDto getValueConfig(int organizationId, String sensorSn, int sensorTypeId) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<String> request = new HttpEntity<>(httpHeaders);
        ResponseEntity<ConfigsDto> exchange = restTemplate.exchange(
                gatewayDomain + VALUE_CONFIG_URL + "/{organizationId}/sensor/{sensorSn}/sensorType/{sensorTypeId}/config",
                HttpMethod.GET,
                request,
                new ParameterizedTypeReference<>() {
                },organizationId,sensorSn,sensorTypeId);
        return exchange.getBody();
    }

    @Override
    public ValueConfigDto createValueConfig(ValueConfigRequest valueConfigRequest, int organizationId, String sensorSn, int sensorTypeId) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<ValueConfigRequest> request = new HttpEntity<>(valueConfigRequest, httpHeaders);
        ResponseEntity<ValueConfigDto> exchange = restTemplate.exchange(
                gatewayDomain + VALUE_CONFIG_URL + "/{organizationId}/sensor/{sensorSn}/sensorType/{sensorTypeId}/value",
                HttpMethod.POST,
                request,
                new ParameterizedTypeReference<>() {
                },organizationId,sensorSn,sensorTypeId);
        return exchange.getBody();
    }

    @Override
    public ValueConfigDto modifyValueConfig(ValueConfigRequest valueConfigRequest, int organizationId, String sensorSn, int sensorTypeId, int valueConfigId) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<ValueConfigRequest> request = new HttpEntity<>(valueConfigRequest, httpHeaders);
        ResponseEntity<ValueConfigDto> exchange = restTemplate.exchange(
                gatewayDomain + VALUE_CONFIG_URL + "/{organizationId}/sensor/{sensorSn}/sensorType/{sensorTypeId}/value/{valueConfigId}",
                HttpMethod.PUT,
                request,
                new ParameterizedTypeReference<>() {
                },organizationId,sensorSn,sensorTypeId, valueConfigId);
        return exchange.getBody();
    }
}
