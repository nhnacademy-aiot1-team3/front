package live.databo3.front.viewer.controller;

import live.databo3.front.owner.dto.BatteryLevelDto;
import live.databo3.front.service.BatteryLevelService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/viewer/battery-levels")
@RequiredArgsConstructor
public class ViewerBatteryLevelController {

    private final BatteryLevelService batteryLevelService;

    /**
     * 회사별 배터리 잔량을 가져오는 메소드
     * @param model 회사별 BatteryLevelDto 리스트를 담은 리스트를 batteryLevelList로 담음
     * @return owner/battery_level로 이동
     * @since 1.0.0
     */
    @GetMapping
    public String getBatteryLevels(Model model) {
        List<List<BatteryLevelDto>> batteryLevelList = batteryLevelService.getBatteryLevels();
        model.addAttribute("batteryLevelLists", batteryLevelList);

        return "viewer/battery_level";
    }
}
