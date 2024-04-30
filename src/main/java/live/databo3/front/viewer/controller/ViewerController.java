package live.databo3.front.viewer.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * viewer 페이지 관련 controller
 *
 * @author 나채현
 * @version 1.0.2
 */
@Slf4j
@Controller
@RequiredArgsConstructor
public class ViewerController {
    @GetMapping("/viewer/my-page")
    public String getViewerMyPage(){
        return "viewer/my_page";
    }

    @GetMapping("/viewer/sensor-list")
    public String getSensorList(){
        return "viewer/sensor_list";
    }

    @GetMapping("/viewer/battery-level")
    public String getBatteryLevel(){
        return "viewer/battery_level";
    }


    /**
     * 공지사항으로 이동하는 method
     * @return 공지사항 페이지로 이동
     */
    @GetMapping("/viewer/notification")
    public String getNotification(){
        return "/viewer/notification";
    }
}
