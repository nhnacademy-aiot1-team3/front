package live.databo3.front.admin.adaptor;

import live.databo3.front.admin.dto.GetNotificationDto;
import live.databo3.front.admin.dto.GetNotificationListResponse;
import live.databo3.front.admin.dto.NotificationDto;

import java.util.List;

public interface ANotificationAdaptor {
    List<GetNotificationListResponse> getAllNotifications();

    GetNotificationDto getNotification(Long notificationId);

    void postNotification(NotificationDto notification);

    void putNotification(Long notificationId);

    void deleteNotification(Long notificationId);
}
