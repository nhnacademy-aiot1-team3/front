package live.databo3.front.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * accessToken, refreshToken, accessToken 만료 시간, refreshToken 만료 시간을 담은 dto
 * @author 이지현
 * @version 1.0.0
 */
@Getter
@ToString
@NoArgsConstructor
public class TokenResponseDto {
    private String accessToken;
    private String refreshToken;
    private Long accessTokenExpireTime;
    private Long refreshTokenExpireTime;
}