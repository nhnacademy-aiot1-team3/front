package live.databo3.front.owner.dto;

import live.databo3.front.dto.SensorTypeDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ErrorLogResponseDto {
    private Long logId;
    private SensorFullDto sensor;
    private SensorTypeDto sensorType;
    private LocalDateTime timestamp;
    private Double value;
    private String errorMsg;
}
