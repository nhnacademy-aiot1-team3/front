package live.databo3.front.admin.controller;

import live.databo3.front.adaptor.ANotificationAdaptor;
import live.databo3.front.admin.dto.GetNotificationDto;
import live.databo3.front.admin.dto.GetNotificationsResponse;
import live.databo3.front.admin.dto.NotificationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/admin")
public class AdminNotificationController {
    private final ANotificationAdaptor notificationAdaptor;

    @GetMapping("/notifications")
    public String getNotifications(Model model) {
        List<GetNotificationsResponse> responseList = notificationAdaptor.getAllNotifications();
        model.addAttribute("notificationList", responseList);
        return "/admin/notification";
    }

    @GetMapping("/notifications/{notificationId}")
    public String getNotification(Model model, @PathVariable String notificationId) {
        GetNotificationDto notification = notificationAdaptor.getNotification(notificationId);
        model.addAttribute("notification", notification);
        return "/admin/notice_writer";
    }

    @PostMapping("/notifications/add")
    public String postNotification(NotificationDto notification) {
        try{

            notificationAdaptor.postNotification(notification);
        } catch (Exception e){
            e.getStackTrace();
        }

        // 1. alert로 가서 성공했다 띄워주기
        // 2. REDIRECT로 해서 GetNotification으로 가져와서 보여주던가
         return "redirect:/admin/notifications";
    }

    @PostMapping("/notifications/{notification}/modify")
    public String putNotification(Model model, @PathVariable Long notificationId) {
        notificationAdaptor.putNotification(notificationId);
        return "/admin/notice_writer";
    }

    @PostMapping("/notifications/{notificationId}/delete")
    public String deleteNotification(Model model, @PathVariable Long notificationId) {
        notificationAdaptor.deleteNotification(notificationId);
        return "redirect:/admin/notifications";
    }
}
