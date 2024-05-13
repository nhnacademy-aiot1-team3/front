package live.databo3.front.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SensorRequest {
    private String sensorSn;
    private String sensorName;
    private String sensorPlace;
}
