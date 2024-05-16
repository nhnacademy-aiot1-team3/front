package live.databo3.front.admin.adaptor;

import live.databo3.front.admin.dto.SensorDto;
import live.databo3.front.admin.dto.request.SensorRequest;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface SensorAdaptor {
    List<SensorDto> getSensorsByOrganization(int organizationId);

    SensorDto createSensor(SensorRequest sensorRequest, int organizationId);

    void deleteSensor(int organizationId, String sensorSn);
}
