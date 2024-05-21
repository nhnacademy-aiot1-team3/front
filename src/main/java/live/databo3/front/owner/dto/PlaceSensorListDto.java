package live.databo3.front.owner.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class PlaceSensorListDto {
    private Integer placeId;
    private String placeName;
    private List<SensorSnNameDto> sensors;
}
