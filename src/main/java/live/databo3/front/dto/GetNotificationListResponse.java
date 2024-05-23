package live.databo3.front.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class GetNotificationListResponse {
    private Long notificationId;
    private String title;
    private String author;
    private LocalDateTime date;

}
