package live.databo3.front.adaptor;


import live.databo3.front.admin.dto.ConfigsDto;
import live.databo3.front.admin.dto.ValueConfigDto;
import live.databo3.front.admin.dto.request.ValueConfigRequest;

public interface ValueConfigAdaptor {
    ConfigsDto getValueConfig(int organizationId, String sensorSn, int sensorTypeId);

    ValueConfigDto createValueConfig(ValueConfigRequest valueConfigRequest, int organizationId, String sensorSn, int sensorTypeId);

    ValueConfigDto modifyValueConfig(ValueConfigRequest valueConfigRequest, int organizationId, String sensorSn, int sensorTypeId, int valueConfigId);

}
