package live.databo3.front.admin.adaptor.impl;

import live.databo3.front.admin.adaptor.OrganizationAdaptor;
import live.databo3.front.admin.dto.*;
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
public class OrganizationAdaptorImpl implements OrganizationAdaptor {

    private final String ORGANIZATION_URL = "/api/account/organizations";
    private final String SENSOR_URL = "/api/sensor/org";


    private final RestTemplate restTemplate;

    @Value("${gateway.api.url}")
    String gatewayDomain;

    @Override
    public List<OrganizationDto> getOrganizations() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<String> request = new HttpEntity<>(httpHeaders);
        ResponseEntity<List<OrganizationDto>> exchange = restTemplate.exchange(
                gatewayDomain + ORGANIZATION_URL,
                HttpMethod.GET,
                request,
                new ParameterizedTypeReference<>() {
                });
        return exchange.getBody();
    }

    @Override
    public OrganizationDto getOrganization(int organizationId) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<String> request = new HttpEntity<>(httpHeaders);
        return restTemplate.exchange(
                gatewayDomain + ORGANIZATION_URL +"/{organizationId}",
                HttpMethod.GET,
                request,
                OrganizationDto.class
                ,organizationId
        ).getBody();
    }

//    @Override
//    public OrganizationDto getMemberByState(int organizationId) {
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
//        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));
//
//        HttpEntity<String> request = new HttpEntity<>(httpHeaders);
//        return restTemplate.exchange(
//                gatewayDomain + ORGANIZATION_URL +"/{organizationId}",
//                HttpMethod.GET,
//                request,
//                OrganizationDto.class
//                ,organizationId
//        ).getBody();
//    }



    @Override
    public String createOrganization(OrganizationRequest organizationRequest) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<OrganizationRequest> request = new HttpEntity<>(organizationRequest, httpHeaders);
        return restTemplate.exchange(
                gatewayDomain + ORGANIZATION_URL,
                HttpMethod.POST,
                request,
                String.class
        ).getBody();
    }

    @Override
    public void deleteOrganization(int organizationId) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));

        restTemplate.delete(
                gatewayDomain + ORGANIZATION_URL + "/{organizationId}",
                HttpMethod.DELETE,
                new ParameterizedTypeReference<>() {
                },organizationId);
    }

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

        restTemplate.delete(
                gatewayDomain + SENSOR_URL + "/{organizationId}/sensor/{sensorSn}",
                HttpMethod.DELETE,
                new ParameterizedTypeReference<>() {
                },organizationId, sensorSn
        );
    }
}
