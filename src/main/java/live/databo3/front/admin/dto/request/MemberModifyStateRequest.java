package live.databo3.front.admin.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberModifyStateRequest {
    private String memberId;
    private int stateId;
}
