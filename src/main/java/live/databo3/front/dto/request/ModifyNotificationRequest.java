package live.databo3.front.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ModifyNotificationRequest {
    private String title;
    private String contents;
}
