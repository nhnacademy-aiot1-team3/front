package live.databo3.front.adaptor;

import live.databo3.front.dto.request.ModifyNotificationRequest;
import live.databo3.front.dto.GetNotificationDto;
import live.databo3.front.dto.GetNotificationListResponse;
import live.databo3.front.dto.NotificationDto;

import java.util.List;

public interface NotificationAdaptor {
    List<GetNotificationListResponse> getAllNotifications();

    GetNotificationDto getNotification(Long notificationId);

    void postNotification(NotificationDto notification);

    void putNotification(Long notificationId, ModifyNotificationRequest request);

    void deleteNotification(Long notificationId);
}