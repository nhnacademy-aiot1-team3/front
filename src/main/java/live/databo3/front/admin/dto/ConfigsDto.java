package live.databo3.front.admin.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class ConfigsDto {
    private Long functionId;
    private String functionName;
    private String deviceSn;
    private String deviceName;
    private List<ValueEntryDto> value;
}
