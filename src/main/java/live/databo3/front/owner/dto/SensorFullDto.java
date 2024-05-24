package live.databo3.front.owner.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SensorFullDto {
    private String sensorSn;
    private String sensorName;
    private Integer placeId;
    private Integer organizationId;
}
