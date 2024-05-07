package live.databo3.front.admin.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrganizationDto {
    private Long organizationId;
    private String organizationName;
    private String gatewaySn;
    private String controllerSn;
}
