package live.databo3.front.owner.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DeviceFullDto {
    private String deviceSn;
    private String deviceName;
    private Integer organizationId;
}
