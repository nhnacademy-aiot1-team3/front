package live.databo3.front.admin.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberDto {
    private int number;
    private String lastLoginDateTime;
    private String email;
    private String id;
    private String role;
    private String state;
}
