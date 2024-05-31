package live.databo3.front.owner.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class SensorListDto {
    private Integer organizationId;
    private String organizationName;
    private List<PlaceSensorListDto> place;
}
