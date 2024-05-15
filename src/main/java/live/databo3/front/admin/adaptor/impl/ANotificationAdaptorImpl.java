package live.databo3.front.admin.adaptor.impl;

import live.databo3.front.admin.adaptor.ANotificationAdaptor;
import live.databo3.front.admin.dto.GetNotificationDto;
import live.databo3.front.admin.dto.GetNotificationsResponse;
import live.databo3.front.admin.dto.NotificationDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RequiredArgsConstructor
@Component
@Slf4j
public class ANotificationAdaptorImpl implements ANotificationAdaptor {
    private final RestTemplate restTemplate;

    private final String APIURL = "/api/account";

    @Value("${gateway.api.url}")
    String gatewayDomain;

    private HttpHeaders getHttpEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        return headers;
    }

    @Override
    public List<GetNotificationsResponse> getAllNotifications() {
        HttpHeaders headers = getHttpEntity();

        HttpEntity<String> request = new HttpEntity<>(headers);
        ResponseEntity<List<GetNotificationsResponse>> exchange = restTemplate.exchange(
                gatewayDomain + APIURL +"/notifications",
                HttpMethod.GET,
                request,
                new ParameterizedTypeReference<>() {}
        );
        return exchange.getBody();
    }

    @Override
    public GetNotificationDto getNotification(String notificationId) {
        HttpHeaders headers = getHttpEntity();

        HttpEntity<String> request = new HttpEntity<>(headers);
        ResponseEntity<GetNotificationDto> exchange = restTemplate.exchange(
                gatewayDomain + APIURL + "/notifications/{notificationId}",
                HttpMethod.GET,
                request,
                new ParameterizedTypeReference<>() {}
        );

        return exchange.getBody();
    }

    @Override
    public void postNotification(NotificationDto notification) {
        HttpHeaders headers = getHttpEntity();

        HttpEntity<NotificationDto> request = new HttpEntity<>(notification, headers);
        restTemplate.exchange(
                gatewayDomain + APIURL + "/notifications",
                HttpMethod.POST,
                request,
                new ParameterizedTypeReference<>() {
                });
    }

    @Override
    public void putNotification(Long notificationId) {
        HttpHeaders headers = getHttpEntity();
        HttpEntity<String> request = new HttpEntity<>(headers);

        restTemplate.exchange(
                gatewayDomain + APIURL + "/notifications/{notificationId}",
                HttpMethod.PUT,
                request,
                Void.class
        );
    }

    @Override
    public void deleteNotification(Long notificationId) {
        HttpHeaders headers = getHttpEntity();
        HttpEntity<String> request = new HttpEntity<>(headers);

        restTemplate.exchange(
                gatewayDomain + APIURL + "/notifications/{notificationId}",
                HttpMethod.DELETE,
                request,
                Void.class
        );
    }
}
