package live.databo3.front.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberOrganizationDto {
    private String memberId;
    private String memberEmail;
    private String roleName;
    private String state;
}
