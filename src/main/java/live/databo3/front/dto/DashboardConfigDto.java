package live.databo3.front.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DashboardConfigDto {
    private Long configId;
    private String organization;
    private String place;
    private String sensorType;
    private String sensorSn;
    private Long sequenceNumber;
    private String chartType;
}