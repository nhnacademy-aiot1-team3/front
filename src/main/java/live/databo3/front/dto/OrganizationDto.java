package live.databo3.front.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrganizationDto {
    private Integer organizationId;
    private String organizationName;
    private String gatewaySn;
    private String controllerSn;
    private Integer state;
    private String roleName;
}
