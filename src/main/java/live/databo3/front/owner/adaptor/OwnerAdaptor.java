package live.databo3.front.owner.adaptor;

import live.databo3.front.owner.dto.SensorListDto;

import java.util.List;

public interface OwnerAdaptor {
    List<SensorListDto> getPlacesBySensorType(Integer sensorType);
}