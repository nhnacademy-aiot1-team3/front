package live.databo3.front.util;

import live.databo3.front.auth.excepiton.InvalidTokenException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Jwt 관련된 유틸 클래스
 *
 * @author 양현성
 */

@Component
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "jwt")
public class JwtUtil {
    private String secret;

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public Claims parseClaims(String token) {
        try {
            Jws<Claims> claimsJws = Jwts.parserBuilder().setSigningKey(secret).build().parseClaimsJws(token);
            return claimsJws.getBody();

        } catch (SignatureException | MalformedJwtException e) {
            throw new InvalidTokenException("유효하지 않는 토큰입니다");
        } catch (ExpiredJwtException e) {
            throw new InvalidTokenException("만료된 토큰입니다");
        }
    }
}
