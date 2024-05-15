package live.databo3.front.admin.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class GetNotificationsResponse {
    private Long notificationId;
    private String title;
    private String author;
    private LocalDateTime date;
}
