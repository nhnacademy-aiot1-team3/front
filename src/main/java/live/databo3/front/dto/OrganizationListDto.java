package live.databo3.front.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrganizationListDto {
    private int state;
    private String roleName;
    private Integer organizationId;
    private String organizationName;
}
