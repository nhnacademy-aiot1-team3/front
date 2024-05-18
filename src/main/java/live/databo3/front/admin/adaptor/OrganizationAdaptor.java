package live.databo3.front.admin.adaptor;

import live.databo3.front.admin.dto.*;
import live.databo3.front.admin.dto.request.GatewayControllerRequest;
import live.databo3.front.admin.dto.request.OrganizationRequest;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface OrganizationAdaptor {

    List<OrganizationDto> getOrganizations();

    OrganizationDto getOrganization(int organizationId);

    List<MemberOrganizationDto> getMemberByState(int organizationId, int stateId, String roleName);

    String modifyMemberState(int organizationId, String memberId, int stateId);

    void deleteOrganizationOwner(int organizationId, String memberId);

    void deleteOrganizationViewer(int organizationId, String memberId);

    String createOrganization(OrganizationRequest organizationRequest);

    String modifyOrganization(int organizationId, OrganizationRequest organizationRequest);

    String modifySerialNumber(int organizationId, OrganizationRequest organizationRequest);

    void deleteOrganization(int organizationId);
}
