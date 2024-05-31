package live.databo3.front.adaptor.impl;

import live.databo3.front.adaptor.MainConfigurationAdaptor;
import live.databo3.front.owner.dto.MainConfigurationCreateDto;
import live.databo3.front.owner.dto.MainConfigurationDto;
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
public class MainConfigurationAdaptorImpl implements MainConfigurationAdaptor {

    private final String MAIN_CONFIGURATION = "/api/account/config/dashboard";
    private final RestTemplate restTemplate;

    @Value("${gateway.api.url}")
    private String gatewayDomain;


    @Override
    public List<MainConfigurationDto> getMainConfiguration() {

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<String> request = new HttpEntity<>(httpHeaders);
        ResponseEntity<List<MainConfigurationDto>> exchange = restTemplate.exchange(
                gatewayDomain + MAIN_CONFIGURATION,
                HttpMethod.GET,
                request,
                new ParameterizedTypeReference<>() {
                }
        );

        return exchange.getBody();
    }

    @Override
    public String createMainConfiguration(MainConfigurationCreateDto mainConfigurationCreateDto) {

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<MainConfigurationCreateDto> request = new HttpEntity<>(mainConfigurationCreateDto, httpHeaders);

        return restTemplate.exchange(
                gatewayDomain + MAIN_CONFIGURATION,
                HttpMethod.POST,
                request,
                String.class
        ).getBody();
    }

}
