package live.databo3.front.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OrganizationRequest {
    private String organizationName;
    private String gatewaySn;
    private String controllerSn;
}
