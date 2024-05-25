package live.databo3.front.adaptor.impl;

import live.databo3.front.adaptor.SettingFunctionTypeAdaptor;
import live.databo3.front.admin.dto.OrganizationDto;
import live.databo3.front.dto.SettingFunctionTypeDto;
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
public class SettingFunctionTypeAdaptorImpl implements SettingFunctionTypeAdaptor {

    private final RestTemplate restTemplate;

    private final String SETTINGFUNCTION_URL = "/api/sensor/settingFunction";

    @Value("${gateway.api.url}")
    private String gatewayDomain;


    @Override
    public List<SettingFunctionTypeDto> getSettingFunctionTypes() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<String> request = new HttpEntity<>(httpHeaders);
        ResponseEntity<List<SettingFunctionTypeDto>> exchange = restTemplate.exchange(
                gatewayDomain + SETTINGFUNCTION_URL,
                HttpMethod.GET,
                request,
                new ParameterizedTypeReference<>() {
                });
        return exchange.getBody();
    }
}
