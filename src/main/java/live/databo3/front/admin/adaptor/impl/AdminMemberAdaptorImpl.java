package live.databo3.front.admin.adaptor.impl;

import live.databo3.front.admin.adaptor.AdminMemberAdaptor;
import live.databo3.front.admin.dto.MemberDto;
import live.databo3.front.admin.dto.MemberModifyStateRequest;
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
public class AdminMemberAdaptorImpl implements AdminMemberAdaptor {

    private final String MEMBER_URL = "/api/account/member";


    private final RestTemplate restTemplate;

    @Value("${gateway.api.url}")
    String gatewayDomain;

    @Override
    public List<MemberDto> getMembers() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<String> request = new HttpEntity<>(httpHeaders);
        ResponseEntity<List<MemberDto>> exchange = restTemplate.exchange(
                gatewayDomain + MEMBER_URL + "/list",
                HttpMethod.GET,
                request,
                new ParameterizedTypeReference<>() {
                });
        return exchange.getBody();
    }

    @Override
    public List<MemberDto> getMembersByRoleAndState(int roleId, int stateId) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<String> request = new HttpEntity<>(httpHeaders);
        ResponseEntity<List<MemberDto>> exchange = restTemplate.exchange(
                gatewayDomain + MEMBER_URL + "/list?roleId={roleId}&stateId={stateID}",
                HttpMethod.GET,
                request,
                new ParameterizedTypeReference<>() {
                }, roleId, stateId);
        return exchange.getBody();
    }

    @Override
    public void modifyMember(MemberModifyStateRequest memberModifyStateRequest) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<MemberModifyStateRequest> request = new HttpEntity<>(memberModifyStateRequest, httpHeaders);
        ResponseEntity<Object> exchange = restTemplate.exchange(
                gatewayDomain + MEMBER_URL + "/modify/state",
                HttpMethod.PUT,
                request,
                new ParameterizedTypeReference<>() {
                }
        );
        exchange.getBody();
    }

}
