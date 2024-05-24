package live.databo3.front.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@NoArgsConstructor
public class GetNotificationListFormatResponse {
    private Long notificationId;
    private String title;
    private String author;
    private String date;

    @Builder
    public GetNotificationListFormatResponse(Long notificationId, String title, String author, LocalDateTime localDateTime) {
        this.notificationId = notificationId;
        this.title = title;
        this.author = author;
        this.date = localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }
}
