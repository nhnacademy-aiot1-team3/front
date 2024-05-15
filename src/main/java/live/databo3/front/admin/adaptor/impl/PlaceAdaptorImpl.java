package live.databo3.front.admin.adaptor.impl;

import live.databo3.front.admin.adaptor.PlaceAdaptor;
import live.databo3.front.admin.dto.PlaceDto;
import live.databo3.front.admin.dto.PlaceRequest;
import live.databo3.front.admin.dto.SensorDto;
import live.databo3.front.admin.dto.SensorRequest;
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
public class PlaceAdaptorImpl implements PlaceAdaptor {
    private final String PLACE_URL = "/api/sensor/org";


    private final RestTemplate restTemplate;

    @Value("${gateway.api.url}")
    String gatewayDomain;

    @Override
    public List<PlaceDto> getPlaces(int organizationId) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<String> request = new HttpEntity<>(httpHeaders);
        ResponseEntity<List<PlaceDto>> exchange = restTemplate.exchange(
                gatewayDomain + PLACE_URL + "/{organizationId}/place",
                HttpMethod.GET,
                request,
                new ParameterizedTypeReference<>() {
                },organizationId);
        return exchange.getBody();
    }

    @Override
    public PlaceDto createPlace(PlaceRequest placeRequest, int organizationId) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<PlaceRequest> request = new HttpEntity<>(placeRequest, httpHeaders);
        ResponseEntity<PlaceDto> exchange = restTemplate.exchange(
                gatewayDomain + PLACE_URL + "/{organizationId}/place",
                HttpMethod.POST,
                request,
                new ParameterizedTypeReference<>() {
                },organizationId
        );
        return exchange.getBody();
    }

    @Override
    public PlaceDto modifyPlace(PlaceRequest placeRequest, int organizationId, int placeId) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<PlaceRequest> request = new HttpEntity<>(placeRequest, httpHeaders);
        ResponseEntity<PlaceDto> exchange = restTemplate.exchange(
                gatewayDomain + PLACE_URL + "/{organizationId}/place/{placeId}",
                HttpMethod.PUT,
                request,
                new ParameterizedTypeReference<>() {
                },organizationId,placeId
        );
        return exchange.getBody();
    }

    @Override
    public void deletePlace(int organizationId, int placeId) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<String> request = new HttpEntity<>(httpHeaders);
        ResponseEntity<PlaceDto> exchange = restTemplate.exchange(
                gatewayDomain + PLACE_URL + "/{organizationId}/place/{placeId}",
                HttpMethod.DELETE,
                request,
                new ParameterizedTypeReference<>() {
                },organizationId,placeId
        );
    }
}
