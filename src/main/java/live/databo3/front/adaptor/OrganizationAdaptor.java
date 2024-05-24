package live.databo3.front.adaptor;

import live.databo3.front.admin.dto.*;
import live.databo3.front.admin.dto.request.OrganizationRequest;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface OrganizationAdaptor {

    List<OrganizationDto> getOrganizations();

    OrganizationDto getOrganization(int organizationId);

    List<MemberOrganizationDto> getMemberByState(int organizationId, int stateId, String roleName);

    List<OrganizationDto> getOrganizationsWithoutMember();

    List<OrganizationDto> getOrganizationsByMember();

    String modifyMemberState(int organizationId, String memberId, int stateId);

    void deleteOrganizationMember(int organizationId, String memberId);

    String createOrganization(OrganizationRequest organizationRequest);

    String postMemberOrgs(int organizationId);

    String modifyOrganization(int organizationId, OrganizationRequest organizationRequest);

    String modifySerialNumber(int organizationId, OrganizationRequest organizationRequest);

    void deleteOrganization(int organizationId);
}
