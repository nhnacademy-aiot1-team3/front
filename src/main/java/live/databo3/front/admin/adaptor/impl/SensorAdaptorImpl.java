package live.databo3.front.admin.adaptor.impl;

import live.databo3.front.admin.adaptor.SensorAdaptor;
import live.databo3.front.admin.dto.SensorDto;
import live.databo3.front.admin.dto.request.SensorRequest;
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
public class SensorAdaptorImpl implements SensorAdaptor {

    private final String SENSOR_URL = "/api/sensor/org";


    private final RestTemplate restTemplate;

    @Value("${gateway.api.url}")
    String gatewayDomain;

    @Override
    public List<SensorDto> getSensorsByOrganization(int organizationId) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<String> request = new HttpEntity<>(httpHeaders);
        ResponseEntity<List<SensorDto>> exchange = restTemplate.exchange(
                gatewayDomain + SENSOR_URL + "/{organizationId}/sensor",
                HttpMethod.GET,
                request,
                new ParameterizedTypeReference<>() {
                },organizationId);
        return exchange.getBody();
    }

    @Override
    public SensorDto createSensor(SensorRequest sensorRequest, int organizationId) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<SensorRequest> request = new HttpEntity<>(sensorRequest, httpHeaders);
        ResponseEntity<SensorDto> exchange = restTemplate.exchange(
                gatewayDomain + SENSOR_URL + "/{organizationId}/sensor",
                HttpMethod.POST,
                request,
                new ParameterizedTypeReference<>() {
                },organizationId
        );
        return exchange.getBody();
    }

    @Override
    public void deleteSensor(int organizationId, String sensorSn) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<String> request = new HttpEntity<>(httpHeaders);
        restTemplate.exchange(
                gatewayDomain + SENSOR_URL + "/{organizationId}/sensor/{sensorSn}",
                HttpMethod.DELETE,
                request,
                new ParameterizedTypeReference<>() {
                },organizationId, sensorSn
        );
    }
}
