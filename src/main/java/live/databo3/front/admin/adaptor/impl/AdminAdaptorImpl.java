package live.databo3.front.admin.adaptor.impl;

import live.databo3.front.admin.adaptor.AdminAdaptor;
import live.databo3.front.admin.dto.OrganizationDto;
import live.databo3.front.admin.dto.OrganizationRequest;
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
public class AdminAdaptorImpl implements AdminAdaptor {

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
                gatewayDomain + "api/account/organizations",
                HttpMethod.GET,
                request,
                new ParameterizedTypeReference<>() {
                });
        return exchange.getBody();
    }

    @Override
    public OrganizationDto getOrganization(String organizationId) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<String> request = new HttpEntity<>(httpHeaders);
        return restTemplate.exchange(
                gatewayDomain + "api/account/organizations/{organizationId}",
                HttpMethod.GET,
                request,
                OrganizationDto.class
                ,organizationId
        ).getBody();
    }


    @Override
    public String postOrganization(OrganizationRequest organizationRequest) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<OrganizationRequest> request = new HttpEntity<>(organizationRequest, httpHeaders);
        return restTemplate.exchange(
                gatewayDomain + "api/account/organizations",
                HttpMethod.POST,
                request,
                String.class
        ).getBody();
    }

}
