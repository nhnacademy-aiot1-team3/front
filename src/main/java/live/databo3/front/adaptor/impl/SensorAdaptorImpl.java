package live.databo3.front.adaptor.impl;

import live.databo3.front.adaptor.SensorAdaptor;
import live.databo3.front.dto.*;
import live.databo3.front.dto.request.DeviceRequest;
import live.databo3.front.dto.request.SensorRequest;
import live.databo3.front.owner.dto.SensorListDto;
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
    public SensorDto modifySensor(int organizationId, String sensorSn, SensorRequest sensorRequest) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<SensorRequest> request = new HttpEntity<>(sensorRequest, httpHeaders);
        ResponseEntity<SensorDto> exchange = restTemplate.exchange(
                gatewayDomain + SENSOR_URL + "/{organizationId}/sensor/{sensorSn}",
                HttpMethod.PUT,
                request,
                new ParameterizedTypeReference<>() {
                },organizationId, sensorSn
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

    @Override
    public SensorTypeMappingDto createSensorType(int organizationId, String sensorSn, int sensorTypeId) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<String> request = new HttpEntity<>(httpHeaders);
        ResponseEntity<SensorTypeMappingDto> exchange = restTemplate.exchange(
                gatewayDomain + SENSOR_URL + "/{organizationId}/sensor/{sensorSn}/sensorTypeId/{sensorTypeId}/sensorTypeMapping",
                HttpMethod.POST,
                request,
                new ParameterizedTypeReference<>() {
                },organizationId, sensorSn, sensorTypeId
        );
        return exchange.getBody();
    }

    @Override
    public void deleteSensorType(int organizationId, String sensorSn, int sensorTypeId) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<String> request = new HttpEntity<>(httpHeaders);
        restTemplate.exchange(
                gatewayDomain + SENSOR_URL + "/{organizationId}/sensor/{sensorSn}/sensorTypeId/{sensorTypeId}/sensorTypeMapping",
                HttpMethod.DELETE,
                request,
                new ParameterizedTypeReference<>() {
                },organizationId, sensorSn, sensorTypeId
        );
    }

    @Override
    public List<SensorTypeMappingListDto> getGetSensorTypeMappingByOrganization(int organizationId) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<String> request = new HttpEntity<>(httpHeaders);
        ResponseEntity<List<SensorTypeMappingListDto>> exchange = restTemplate.exchange(
                gatewayDomain + SENSOR_URL + "/{organizationId}/sensorTypeMapping",
                HttpMethod.GET,
                request,
                new ParameterizedTypeReference<>() {
                },organizationId);
        return exchange.getBody();
    }

    @Override
    public List<SensorTypeDto> getSensorType() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<String> request = new HttpEntity<>(httpHeaders);
        ResponseEntity<List<SensorTypeDto>> exchange = restTemplate.exchange(
                gatewayDomain +  "/api/sensor/sensorType",
                HttpMethod.GET,
                request,
                new ParameterizedTypeReference<>() {
                });
        return exchange.getBody();
    }

    @Override
    public List<DeviceDto> getDevice(int organizationId) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<String> request = new HttpEntity<>(httpHeaders);
        ResponseEntity<List<DeviceDto>> exchange = restTemplate.exchange(
                gatewayDomain + SENSOR_URL + "/{organizationId}/device",
                HttpMethod.GET,
                request,
                new ParameterizedTypeReference<>() {
                },organizationId);
        return exchange.getBody();
    }

    @Override
    public SensorDto createDevice(DeviceRequest deviceRequest, int organizationId) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<DeviceRequest> request = new HttpEntity<>(deviceRequest, httpHeaders);
        ResponseEntity<SensorDto> exchange = restTemplate.exchange(
                gatewayDomain + SENSOR_URL + "/{organizationId}/device",
                HttpMethod.POST,
                request,
                new ParameterizedTypeReference<>() {
                },organizationId
        );
        return exchange.getBody();
    }

    @Override
    public DeviceDto modifyDevice(int organizationId, String deviceSn, DeviceRequest deviceRequest) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<DeviceRequest> request = new HttpEntity<>(deviceRequest, httpHeaders);
        ResponseEntity<DeviceDto> exchange = restTemplate.exchange(
                gatewayDomain + SENSOR_URL + "/{organizationId}/device/{deviceSn}",
                HttpMethod.PUT,
                request,
                new ParameterizedTypeReference<>() {
                },organizationId, deviceSn
        );
        return exchange.getBody();
    }

    @Override
    public void deleteDevice(int organizationId, String deviceSn) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<String> request = new HttpEntity<>(httpHeaders);
        restTemplate.exchange(
                gatewayDomain + SENSOR_URL + "/{organizationId}/device",
                HttpMethod.DELETE,
                request,
                new ParameterizedTypeReference<>() {
                },organizationId, deviceSn
        );
    }

    @Override
    public List<SensorListDto> getOrganizationListBySensorType(int sensorType) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<String> request = new HttpEntity<>(headers);
        ResponseEntity<List<SensorListDto>> exchange = restTemplate.exchange(
                gatewayDomain + "/api/sensor/sensorList?sensorTypeId={sensorType}",
                HttpMethod.GET,
                request,
                new ParameterizedTypeReference<>(){
                }, sensorType);
        return exchange.getBody();
    }

}
