package live.databo3.front.admin.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class IdCurrentModifyPassWordRequest {
    private String id;
    private String currentPassword;
    private String modifyPassword;
}
