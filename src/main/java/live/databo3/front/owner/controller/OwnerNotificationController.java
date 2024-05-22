package live.databo3.front.owner.controller;

import live.databo3.front.dto.GetNotificationFormatResponse;
import live.databo3.front.dto.GetNotificationListFormatResponse;
import live.databo3.front.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * owner의 공지사항 관련 controller
 * @author jihyeon
 * @since 1.0.0
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/owner/notifications")
public class OwnerNotificationController {
    private final NotificationService notificationService;

    /**
     * 모든 공지사항을 가져오는 메소드
     * @param model 모든 공지사항을 notificationList로 담음
     * @return admin/notifiationList로 이동
     * @since 1.0.0
     */
    @GetMapping
    public String getNotifications(Model model) {
        List<GetNotificationListFormatResponse> responseList = notificationService.getNotifications();
        model.addAttribute("notificationList", responseList);
        return "owner/notification_list";
    }

    /**
     * 특정 공지사항을 가져오는 메소드
     * @param model 특정 공지사항을 notification으로 담음
     * @param notificationId 공지사항 담당 번호
     * @return admin/notification으로 이동
     * @since 1.0.0
     */
    @GetMapping("/{notificationId}")
    public String getNotification(Model model, @PathVariable Long notificationId) {
        GetNotificationFormatResponse notification = notificationService.getNotification(notificationId);
        model.addAttribute("notification", notification);
        return "owner/notification";

    }

}
