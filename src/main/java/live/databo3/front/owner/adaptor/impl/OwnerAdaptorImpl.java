package live.databo3.front.owner.adaptor.impl;

import live.databo3.front.owner.adaptor.OwnerAdaptor;
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
public class OwnerAdaptorImpl implements OwnerAdaptor {
    private final String SENSORLIST_URL = "/api/sensor";

    private final RestTemplate restTemplate;

    @Value("${gateway.api.url}")
    private String gatewayDomain;

    @Override
    public List<SensorListDto> getPlacesBySensorType(Integer sensorType) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<String> request = new HttpEntity<>(headers);
        ResponseEntity<List<SensorListDto>> exchange = restTemplate.exchange(
                gatewayDomain + SENSORLIST_URL + "/sensorList?sensorTypeId={sensorType}",
                HttpMethod.GET,
                request,
                new ParameterizedTypeReference<>() {},
                sensorType
        );
        return exchange.getBody();
    }
}
