package live.databo3.front.admin.controller;

import live.databo3.front.admin.adaptor.ANotificationAdaptor;
import live.databo3.front.admin.dto.GetNotificationFormatResponse;
import live.databo3.front.admin.dto.GetNotificationListFormatResponse;
import live.databo3.front.admin.dto.NotificationDto;
import live.databo3.front.admin.service.AdminNotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * 공지사항 관련 Controller
 * @author jihyeon
 * @version  1.0.0
 */
@RequiredArgsConstructor
@Controller
@RequestMapping("/admin")
public class AdminNotificationController {

    private final ANotificationAdaptor notificationAdaptor;
    private final AdminNotificationService adminNotificationService;

    /**
     * 모든 공지사항을 가져오는 메소드
     * @param model 모든 공지사항을 notificationList로 담음
     * @return admin/notifiationList로 이동
     * @since 1.0.0
     */
    @GetMapping("/notifications")
    public String getNotifications(Model model) {
        List<GetNotificationListFormatResponse> responseList = adminNotificationService.getNotifications();
        model.addAttribute("notificationList", responseList);
        return "admin/notificationList";
    }

    /**
     * 특정 공지사항을 가져오는 메소드
     * @param model 특정 공지사항을 notification으로 담음
     * @param notificationId 공지사항 담당 번호
     * @return admin/notification으로 이동
     * @since 1.0.0
     */
    @GetMapping("/notifications/{notificationId}")
    public String getNotification(Model model, @PathVariable Long notificationId) {
        GetNotificationFormatResponse notification = adminNotificationService.getNotification(notificationId);
        model.addAttribute("notification", notification);
        return "admin/notification";
    }

    /**
     * 공지사항 작성하는 메소드
     * @param notification title, contents 제출
     * @return 모든 공지사항 가져오는 메소드로 이동
     * @since 1.0.0
     */
    @PostMapping("/notifications/add")
    public String postNotification(NotificationDto notification) {
        notificationAdaptor.postNotification(notification);
        return "redirect:/admin/notifications";
    }

    /**
     * 수정하는 메소드
     * @param notificationId 공지사항 담당 번호
     * @return admin/notification로 이동
     * @since 1.0.0
     */
    @PostMapping("/notifications/{notification}/modify")
    public String putNotification(@PathVariable Long notificationId) {
        notificationAdaptor.putNotification(notificationId);
        return "admin/notification";
    }

    /**
     * 삭제하는 메소드
     * @param notificationId 공지사항 담당 번호
     * @return 모든 공지사항을 가져오는 메소드로 이동
     * @since 1.0.0
     */
    @PostMapping("/notifications/{notificationId}/delete")
    public String deleteNotification(@PathVariable Long notificationId) {
        notificationAdaptor.deleteNotification(notificationId);
        return "redirect:/admin/notifications";
    }
}
