package live.databo3.front.adaptor;

import live.databo3.front.admin.dto.GeneralConfigDto;
import live.databo3.front.admin.dto.request.GeneralConfigRequest;

public interface GeneralConfigAdaptor {
    GeneralConfigDto getGeneralConfig(int organizationId, String sensorSn, int sensorTypeId);

    GeneralConfigDto modifyGeneralConfig(GeneralConfigRequest generalConfigRequest, int organizationId, String sensorSn, int sensorTypeId);

}
