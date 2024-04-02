package live.databo3.front.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 사용자 정보(id, pw)를 제출 하기 위한 dto
 * @author 이지현
 * @version 1.0.0
 */
@AllArgsConstructor
@Getter
public class MemberRequestDto {
    private String id;
    private String password;
}
