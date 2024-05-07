package live.databo3.front.admin.adaptor;

import live.databo3.front.admin.dto.OrganizationDto;
import live.databo3.front.admin.dto.OrganizationRequest;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface AdminAdaptor {

    List<OrganizationDto> getOrganizations();

    OrganizationDto getOrganization(String organizationId);

    String postOrganization(OrganizationRequest organizationRequest);

    }
