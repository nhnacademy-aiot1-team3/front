package live.databo3.front.admin.controller;

import live.databo3.front.adaptor.NotificationAdaptor;
import live.databo3.front.dto.GetNotificationFormatResponse;
import live.databo3.front.dto.GetNotificationListFormatResponse;
import live.databo3.front.admin.dto.NotificationDto;
import live.databo3.front.admin.dto.request.ModifyNotificationRequest;
import live.databo3.front.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 공지사항 관련 Controller
 * @author jihyeon
 * @version  1.0.1
 */
@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/admin/notifications")
public class AdminNotificationController {

    private final NotificationAdaptor notificationAdaptor;
    private final NotificationService adminNotificationService;

    /**
     * 모든 공지사항을 가져오는 메소드
     * @param model 모든 공지사항을 notificationList로 담음
     * @return admin/notifiationList로 이동
     * @since 1.0.0
     */
    @GetMapping
    public String getNotifications(Model model) {
        List<GetNotificationListFormatResponse> responseList = adminNotificationService.getNotifications();
        model.addAttribute("notificationList", responseList);
        return "admin/notification_list";
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
        GetNotificationFormatResponse notification = adminNotificationService.getNotification(notificationId);
        model.addAttribute("notification", notification);
        return "admin/notification";
    }

    /**
     * 공지사항 추가 페이지로 이동
     * @return admin/notification_writer로 페이지 이동
     * @since 1.0.1
     */
    @GetMapping("/add-page")
    public String getNotificationAddPage() {
        return "admin/notification_writer";
    }

    /**
     * 공지사항 수정 페이지로 이동
     * @param model 특정 공지사항(notificaionId, title, contents, date, memberId) 내용을 model에 notification으로 담는다
     * @param notificationId 특정 공지사항 번호
     * @return admin/notification_modify로 이동
     * @since 1.0.1
     */
    @GetMapping("/{notificationId}/modify-page")
    public String getNotificationModifyPage(Model model, @PathVariable Long notificationId) {
        GetNotificationFormatResponse notification = adminNotificationService.getNotification(notificationId);
        model.addAttribute("notification", notification);
        return "admin/notification_modify";
    }
    /**
     * 공지사항 작성하는 메소드
     * @param notification title, contents 제출
     * @return 모든 공지사항 가져오는 메소드로 이동
     * @since 1.0.0
     */
    @PostMapping("/add")
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
    @PostMapping("/{notificationId}/modify")
    public String putNotification(@PathVariable Long notificationId, ModifyNotificationRequest request) {
        notificationAdaptor.putNotification(notificationId, request);
        return "redirect:/admin/notifications/"+notificationId;
    }

    /**
     * 삭제하는 메소드
     * @param notificationId 공지사항 담당 번호
     * @return 모든 공지사항을 가져오는 메소드로 이동
     * @since 1.0.0
     */
    @PostMapping("/{notificationId}/delete")
    public String deleteNotification(@PathVariable Long notificationId) {
        notificationAdaptor.deleteNotification(notificationId);
        return "redirect:/admin/notifications";
    }
}

