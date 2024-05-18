package live.databo3.front.admin.service;

import live.databo3.front.admin.dto.GetNotificationFormatResponse;
import live.databo3.front.admin.dto.GetNotificationListFormatResponse;

import java.util.List;

public interface AdminNotificationService {
    List<GetNotificationListFormatResponse> getNotifications();

    GetNotificationFormatResponse getNotification(Long notificationId);
}
