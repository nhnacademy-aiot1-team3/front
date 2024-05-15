package live.databo3.front.admin.adaptor;

import live.databo3.front.admin.dto.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface OrganizationAdaptor {

    List<OrganizationDto> getOrganizations();

    OrganizationDto getOrganization(int organizationId);

    List<MemberDto> getMemberByState(int organizationId, int stateId, String roleName);

    String createOrganization(OrganizationRequest organizationRequest);

    void deleteOrganization(int organizationId);
}
