package live.databo3.front.member.dto;

import  lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 사용자 정보(id, pw, email)을 받아 오기 위한 request
 * @author 나채현
 * @version 1.0.0
 */
@AllArgsConstructor
@Getter
public class MemberRegisterRequest {
    private String id;
    private String password;
    private String email;
    private int role;
}
