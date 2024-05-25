package live.databo3.front.owner.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class DeviceLogResponseDto {
    private Long logId;
    private DeviceFullDto device;
    private LocalDateTime timestamp;
    private Double value;
}
