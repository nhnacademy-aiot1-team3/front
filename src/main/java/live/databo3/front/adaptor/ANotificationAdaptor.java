package live.databo3.front.adaptor;

import live.databo3.front.admin.dto.GetNotificationDto;
import live.databo3.front.admin.dto.GetNotificationsResponse;
import live.databo3.front.admin.dto.NotificationDto;

import java.util.List;

public interface ANotificationAdaptor {
    List<GetNotificationsResponse> getAllNotifications();

    GetNotificationDto getNotification(String notificationId);

    void postNotification(NotificationDto notification);

    void putNotification(Long notificationId);

    void deleteNotification(Long notificationId);
}
