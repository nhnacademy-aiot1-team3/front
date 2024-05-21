package live.databo3.front.admin.adaptor.impl;

import live.databo3.front.admin.adaptor.ANotificationAdaptor;
import live.databo3.front.admin.dto.GetNotificationDto;
import live.databo3.front.admin.dto.GetNotificationListResponse;
import live.databo3.front.admin.dto.NotificationDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * 공지사항 관련 Adaptor
 * @author jihyeon
 * @version  1.0.0
 */
@RequiredArgsConstructor
@Component
@Slf4j
public class ANotificationAdaptorImpl implements ANotificationAdaptor {
    private final RestTemplate restTemplate;

    private final String APIURL = "/api/account";

    @Value("${gateway.api.url}")
    private String gatewayDomain;

    /**
     * HttpHeaders에 contentType과 Accept에 MediaType.APPLICATIOM_JSON을 헤더 추가
     * @return HttpHeaders
     * @since 1.0.0
     */
    private HttpHeaders getHttpEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        return headers;
    }

    /**
     * 모든 공지사항 들고오는 요청을 gateway로 전송
     * @return notificationId, title, author, date가 담긴 객체의 리스트
     * @since 1.0.0
     */
    @Override
    public List<GetNotificationListResponse> getAllNotifications() {
        HttpHeaders headers = getHttpEntity();

        HttpEntity<String> request = new HttpEntity<>(headers);
        ResponseEntity<List<GetNotificationListResponse>> exchange = restTemplate.exchange(
                gatewayDomain + APIURL +"/notifications",
                HttpMethod.GET,
                request,
                new ParameterizedTypeReference<>() {}
        );
        return exchange.getBody();
    }

    /**
     * 특정 공지사항 가져오는 요청문 gateway에 전송
     * @param notificationId 공지사항 담당 번호
     * @return notificationId, title, contents, date, memberId를 반환
     * @since 1.0.0
     */
    @Override
    public GetNotificationDto getNotification(Long notificationId) {
        HttpHeaders headers = getHttpEntity();

        HttpEntity<String> request = new HttpEntity<>(headers);
        ResponseEntity<GetNotificationDto> exchange = restTemplate.exchange(
                gatewayDomain + APIURL + "/notifications/{notificationId}",
                HttpMethod.GET,
                request,
                GetNotificationDto.class,
                notificationId
        );

        return exchange.getBody();
    }

    /**
     * 공지사항 작성을 위한 요청문 gateway에 저송
     * @param notification title, contents이 담긴 객체
     * @since 1.0.0
     */
    @Override
    public void postNotification(NotificationDto notification) {
        HttpHeaders headers = getHttpEntity();

        HttpEntity<NotificationDto> request = new HttpEntity<>(notification, headers);
        restTemplate.exchange(
                gatewayDomain + APIURL + "/notifications",
                HttpMethod.POST,
                request,
                Void.class
                );
    }

    /**
     * 공지사항 수정 요청문 gateway에 전송
     * @param notificationId 공지사항 담당 번호
     * @since 1.0.0
     */
    @Override
    public void putNotification(Long notificationId) {
        HttpHeaders headers = getHttpEntity();
        HttpEntity<String> request = new HttpEntity<>(headers);

        restTemplate.exchange(
                gatewayDomain + APIURL + "/notifications/{notificationId}",
                HttpMethod.PUT,
                request,
                Void.class,
                notificationId
        );
    }

    /**
     * 공지사항 삭제 요청문 gateway에 전송
     * @param notificationId 공지사항 담당 번호
     * @since 1.0.0
     */
    @Override
    public void deleteNotification(Long notificationId) {
        HttpHeaders headers = getHttpEntity();
        HttpEntity<String> request = new HttpEntity<>(headers);

        restTemplate.exchange(
                gatewayDomain + APIURL + "/notifications/{notificationId}",
                HttpMethod.DELETE,
                request,
                Void.class,
                notificationId
        );
    }
}
