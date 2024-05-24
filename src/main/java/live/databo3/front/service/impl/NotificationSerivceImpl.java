package live.databo3.front.service.impl;

import live.databo3.front.adaptor.NotificationAdaptor;
import live.databo3.front.dto.GetNotificationDto;
import live.databo3.front.dto.GetNotificationFormatResponse;
import live.databo3.front.dto.GetNotificationListFormatResponse;
import live.databo3.front.dto.GetNotificationListResponse;
import live.databo3.front.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 공지사항 service
 * @author jihyeon
 * @version 1.0.0
 */
@Service
@RequiredArgsConstructor
public class NotificationSerivceImpl implements NotificationService {

    private final NotificationAdaptor notificationAdaptor;

    /**
     * adaptor에서 받은 notification 리스트를 dto 리스트로 변경하면서 date를 format한다
     * @return notificationId, title, author, date를 가진 dto의 리스트
     * @since 1.0.0
     */
    @Override
    public List<GetNotificationListFormatResponse> getNotifications() {
        List<GetNotificationListResponse> resultList = notificationAdaptor.getAllNotifications();
        List<GetNotificationListFormatResponse> responseList = new ArrayList<>();
        for(GetNotificationListResponse result : resultList) {
            GetNotificationListFormatResponse formatResponse = GetNotificationListFormatResponse.builder()
                    .notificationId(result.getNotificationId())
                    .title(result.getTitle())
                    .author(result.getAuthor())
                    .localDateTime(result.getDate())
                    .build();
            responseList.add(formatResponse);
        }
        Collections.reverse(responseList);
        return responseList;
    }

    /**
     * 특정 공지사항의 date를 format하여 controller에게 반환
     * @param notificationId 공지사항 담당 번호
     * @return notificationId, title, contents, date, memberId를 가진 dto 반환
     * @since 1.0.0
     */
    @Override
    public GetNotificationFormatResponse getNotification(Long notificationId) {
        GetNotificationDto result = notificationAdaptor.getNotification(notificationId);
        return GetNotificationFormatResponse.builder()
                .notificationId(result.getNotificationId())
                .title(result.getTitle())
                .contents(result.getContents())
                .date(result.getDate())
                .memberId(result.getMemberId())
                .build();
    }
}
