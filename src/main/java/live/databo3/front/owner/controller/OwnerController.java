package live.databo3.front.owner.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


/**
 * owner 페이지 관련 controller
 *
 * @author 나채현
 * @version 1.0.2
 */
@Slf4j
@Controller
@RequiredArgsConstructor
public class OwnerController {
    @GetMapping("/owner/my_page")
    public String getOwnerMyPage(){
        return "owner/my_page";
    }

    @GetMapping("/owner/sensor_list")
    public String getSensorList(){
        return "owner/sensor_list";
    }

    @GetMapping("/owner/battery_level")
    public String getBatteryLevel(){
        return "owner/battery_level";
    }

    /**
     * 공지사항으로 이동하는 method
     * @return 공지사항 페이지로 이동
     */
    @GetMapping("/owner/notification")
    public String getNotification(){
        return "/owner/notification";
    }
}
