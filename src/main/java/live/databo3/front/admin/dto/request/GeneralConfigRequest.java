package live.databo3.front.admin.dto.request;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GeneralConfigRequest {
    private Long functionId;
    private String deviceSn;
}
