package live.databo3.front.adaptor.impl;

import live.databo3.front.adaptor.OrganizationAdaptor;
import live.databo3.front.admin.dto.*;
import live.databo3.front.admin.dto.request.OrganizationRequest;
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

    @Override
    public List<MemberOrganizationDto> getMemberByState(int organizationId, int stateId, String roleName) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<String> request = new HttpEntity<>(httpHeaders);
        ResponseEntity<List<MemberOrganizationDto>> exchange = restTemplate.exchange(
                gatewayDomain + ORGANIZATION_URL +"/{organizationId}/members/state?state={stateId}&role={roleName}",
                HttpMethod.GET,
                request,
                new ParameterizedTypeReference<>() {
                },organizationId, stateId, roleName
        );
        return exchange.getBody();
    }

    @Override
    public List<OrganizationDto> getOrganizationsWithoutMember() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<String> request = new HttpEntity<>(httpHeaders);
        ResponseEntity<List<OrganizationDto>> exchange = restTemplate.exchange(
                gatewayDomain + ORGANIZATION_URL +"/members-without/me",
                HttpMethod.GET,
                request,
                new ParameterizedTypeReference<>() {
                }
        );
        return exchange.getBody();
    }

    @Override
    public List<OrganizationDto> getOrganizationsByMember() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<String> request = new HttpEntity<>(httpHeaders);
        ResponseEntity<List<OrganizationDto>> exchange = restTemplate.exchange(
                gatewayDomain + ORGANIZATION_URL +"/members/me",
                HttpMethod.GET,
                request,
                new ParameterizedTypeReference<>() {
                }
        );
        return exchange.getBody();
    }

    @Override
    public String modifyMemberState(int organizationId, String memberId, int stateId) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<String> request = new HttpEntity<>(httpHeaders);
        ResponseEntity<String> exchange = restTemplate.exchange(
                gatewayDomain + ORGANIZATION_URL +"/{organizationId}/members/{memberId}?state={stateId}",
                HttpMethod.PUT,
                request,
                new ParameterizedTypeReference<>() {
                },organizationId, memberId, stateId
        );
        return exchange.getBody();
    }

    @Override
    public void deleteOrganizationMember(int organizationId, String memberId) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<String> request = new HttpEntity<>(httpHeaders);
        restTemplate.exchange(
                gatewayDomain + ORGANIZATION_URL + "/{organizationId}/members/{memberId}",
                HttpMethod.DELETE,
                request,
                new ParameterizedTypeReference<>() {
                },organizationId,memberId
        );
    }

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
    public String postMemberOrgs(int organizationId) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<OrganizationRequest> request = new HttpEntity<>(httpHeaders);
        ResponseEntity<String> exchange =  restTemplate.exchange(
                gatewayDomain + ORGANIZATION_URL + "/{organizationId}/members",
                HttpMethod.POST,
                request,
                new ParameterizedTypeReference<>() {
                },organizationId
        );
        return exchange.getBody();
    }

    @Override
    public String modifyOrganization(int organizationId, OrganizationRequest organizationRequest) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<OrganizationRequest> request = new HttpEntity<>(organizationRequest, httpHeaders);
        ResponseEntity<String> exchange = restTemplate.exchange(
                gatewayDomain + ORGANIZATION_URL + "/{organizationId}",
                HttpMethod.PUT,
                request,
                new ParameterizedTypeReference<>() {
                },organizationId
        );
        return exchange.getBody();
    }

    @Override
    public String modifySerialNumber(int organizationId, OrganizationRequest organizationRequest) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<OrganizationRequest> request = new HttpEntity<>(organizationRequest, httpHeaders);
        ResponseEntity<String> exchange = restTemplate.exchange(
                gatewayDomain + ORGANIZATION_URL + "/{organizationId}/gatewayAndController",
                HttpMethod.PUT,
                request,
                new ParameterizedTypeReference<>() {
                },organizationId
        );
        return exchange.getBody();
    }

    @Override
    public void deleteOrganization(int organizationId) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<String> request = new HttpEntity<>(httpHeaders);
        restTemplate.exchange(
                gatewayDomain + ORGANIZATION_URL + "/{organizationId}",
                HttpMethod.DELETE,
                request,
                new ParameterizedTypeReference<>() {
                },organizationId
        );
    }
}
