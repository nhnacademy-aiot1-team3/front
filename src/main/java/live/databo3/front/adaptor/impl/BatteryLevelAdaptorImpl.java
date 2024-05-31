package live.databo3.front.adaptor.impl;

import live.databo3.front.adaptor.BatteryLevelAdaptor;
import live.databo3.front.owner.dto.BatteryLevelListDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * battery level 관련 adaptor
 * @author jihyeon
 * @version 1.0.0
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class BatteryLevelAdaptorImpl implements BatteryLevelAdaptor {

    private final String BATTERY_LEVEL_URL = "/api/sensor/battery-levels";
    private final RestTemplate restTemplate;

    @Value("${gateway.api.url}")
    String gatewayDomain;

    /**
     *
     * @param organizationName 회사이름
     * @return BatteryLevelDto(place, device, branch, value) 리스트 반환
     * @since 1.0.0
     */
    @Override
    public BatteryLevelListDto getBatteryLevels(String organizationName) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<String> request = new HttpEntity<>(headers);
        log.info("url: {}", gatewayDomain + BATTERY_LEVEL_URL);
        ResponseEntity<BatteryLevelListDto> exchange = restTemplate.exchange(
                gatewayDomain + BATTERY_LEVEL_URL + "/fields/value/branches/{organizationName}/endpoint/battery_level",
                HttpMethod.GET,
                request,
                new ParameterizedTypeReference<>() {}, organizationName
        );
        return exchange.getBody();
    }
}
