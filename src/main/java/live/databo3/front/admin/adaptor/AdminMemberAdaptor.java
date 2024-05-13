package live.databo3.front.admin.adaptor;

import live.databo3.front.admin.dto.MemberDto;
import live.databo3.front.admin.dto.MemberModifyStateRequest;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface AdminMemberAdaptor {
    public List<MemberDto> getMembers();

    public List<MemberDto> getMembersByRoleAndState(int roleId, int stateId);

    public String modifyMember(MemberModifyStateRequest memberModifyStateRequest);

}
