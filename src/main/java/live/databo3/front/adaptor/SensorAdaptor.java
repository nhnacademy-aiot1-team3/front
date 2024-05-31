package live.databo3.front.adaptor;

import live.databo3.front.dto.*;
import live.databo3.front.dto.request.DeviceRequest;
import live.databo3.front.dto.request.SensorRequest;
import live.databo3.front.owner.dto.SensorListDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface SensorAdaptor {
    List<SensorDto> getSensorsByOrganization(int organizationId);

    SensorDto createSensor(SensorRequest sensorRequest, int organizationId);

    SensorDto modifySensor(int organizationId, String sensorSn, SensorRequest sensorRequest);

    void deleteSensor(int organizationId, String sensorSn);

    SensorTypeMappingDto createSensorType(int organizationId, String sensorSn, int sensorTypeId);

    void deleteSensorType(int organizationId, String sensorSn, int sensorTypeId);

    List<SensorTypeMappingListDto> getGetSensorTypeMappingByOrganization(int organizationId);

    List<SensorTypeDto> getSensorType();

    List<DeviceDto> getDevice(int organizationId);

    SensorDto createDevice(DeviceRequest deviceRequest, int organizationId);

    void deleteDevice(int organizationId, String deviceSn);

    DeviceDto modifyDevice(int organizationId, String deviceSn, DeviceRequest deviceRequest);

    List<SensorListDto> getOrganizationListBySensorType(int sensorType);
}
