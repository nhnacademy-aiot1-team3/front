package live.databo3.front.admin.adaptor;

import live.databo3.front.admin.dto.OrganizationDto;
import live.databo3.front.admin.dto.OrganizationRequest;
import live.databo3.front.admin.dto.SensorDto;
import live.databo3.front.admin.dto.SensorRequest;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface OrganizationAdaptor {

    List<OrganizationDto> getOrganizations();

    OrganizationDto getOrganization(int organizationId);

    String createOrganization(OrganizationRequest organizationRequest);

    void deleteOrganization(int organizationId);

    List<SensorDto> getSensorsByOrganization(int organizationId);

    SensorDto createSensor(SensorRequest sensorRequest, int organizationId);

    void deleteSensor(int organizationId, String sensorSn);
}
