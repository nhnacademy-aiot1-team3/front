package live.databo3.front.adaptor;

import live.databo3.front.owner.dto.DeviceLogResponseDto;

import java.util.List;

public interface DeviceLogAdaptor {
    List<DeviceLogResponseDto> getDeviceLog(Integer organizationId);
}
