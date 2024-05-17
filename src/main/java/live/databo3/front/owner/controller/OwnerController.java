package live.databo3.front.owner.controller;

import live.databo3.front.owner.adaptor.OwnerAdaptor;
import live.databo3.front.owner.dto.SensorListDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;


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
    private final OwnerAdaptor ownerAdaptor;

    @GetMapping("/owner/my-page")
    public String getOwnerMyPage(){
        return "owner/my_page";
    }

    @GetMapping("/owner/sensor-list")
    public String getSensorList(){
        return "owner/sensor_list";
    }

    @GetMapping("/owner/temperature")
    public String getTemperature(Model model){
        List<SensorListDto> sensorList = ownerAdaptor.getPlacesBySensorType(1);
        model.addAttribute("sensorList", sensorList);
        return "owner/temperature";
    }

    @GetMapping("/owner/battery-level")
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

    @GetMapping("/owner/organization-list")
    public String getOrganizationList(Model model){

        return "/owner/organization_list";
    }

    @GetMapping("/owner/organization-management")
    public String getOrganization(){
        return "/owner/organization_management";
    }
}
