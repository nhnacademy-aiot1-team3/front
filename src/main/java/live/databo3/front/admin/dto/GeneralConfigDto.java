package live.databo3.front.admin.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class GeneralConfigDto {
    private Long recordNumber;
    private Long functionId;
    private LocalDateTime lastUpdateDate;
    private String deviceSn;
    private String deviceName;
}
