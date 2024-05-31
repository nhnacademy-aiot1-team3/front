package live.databo3.front.owner.dto;

import lombok.Getter;

@Getter
public class MainConfigurationDto {
    Long configId;
    String organization;
    String place;
    String sensorType;
    String sensorSn;
    Long sequenceNumber;
    String chartType;
}
