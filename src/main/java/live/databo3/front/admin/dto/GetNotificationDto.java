package live.databo3.front.admin.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class GetNotificationDto {
    private Long notificationId;
    private String title;
    private String contents;
    private LocalDateTime date;
    private String memberId;
    private String file;
}