package live.databo3.front.adaptor;

import live.databo3.front.owner.dto.BatteryLevelListDto;

public interface BatteryLevelAdaptor{

    BatteryLevelListDto getBatteryLevels(String organizationName);
}
