package live.databo3.front.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SensorDto {
    private String sensorSn;
    private String sensorName;
    private PlaceDto place;
}
