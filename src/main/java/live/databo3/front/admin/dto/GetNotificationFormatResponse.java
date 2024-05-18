package live.databo3.front.admin.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@NoArgsConstructor
public class GetNotificationFormatResponse {
    private Long notificationId;
    private String title;
    private String contents;
    private String date;
    private String memberId;
    private String file;

    @Builder
    public GetNotificationFormatResponse(Long notificationId, String title, String contents, LocalDateTime date, String memberId, String file) {
        this.notificationId = notificationId;
        this.title = title;
        this.contents = contents;
        this.date = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        this.memberId = memberId;
        this.file = file;
    }
}
