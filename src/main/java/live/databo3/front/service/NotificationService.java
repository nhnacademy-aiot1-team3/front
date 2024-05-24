package live.databo3.front.service;

import live.databo3.front.dto.GetNotificationFormatResponse;
import live.databo3.front.dto.GetNotificationListFormatResponse;

import java.util.List;

public interface NotificationService {
    List<GetNotificationListFormatResponse> getNotifications();

    GetNotificationFormatResponse getNotification(Long notificationId);
}
