package live.databo3.front.auth.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

@Getter
@ToString
@NoArgsConstructor
public class JwtPayloadDto {
    private String sub;
    private String memberId;
    private String memberEmail;
    private List<AuthenticationRoleDto> roles;
    private Long iat;
    private Long exp;
}
