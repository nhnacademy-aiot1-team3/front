package live.databo3.front.service;

import live.databo3.front.owner.dto.BatteryLevelDto;

import java.util.List;

public interface BatteryLevelService {
    List<List<BatteryLevelDto>> getBatteryLevels();
}
