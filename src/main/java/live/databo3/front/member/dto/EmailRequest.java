package live.databo3.front.member.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * email인증을 위한 Email을 받아 오기 위한 request
 * @author 나채현
 * @version 1.0.3
 */
@NoArgsConstructor
@Getter
public class EmailRequest {
    private String email;
}
