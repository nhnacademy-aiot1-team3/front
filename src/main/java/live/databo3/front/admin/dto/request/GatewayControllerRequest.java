package live.databo3.front.admin.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GatewayControllerRequest {
      private String gatewaySn;
      private String controllerSn;
}
