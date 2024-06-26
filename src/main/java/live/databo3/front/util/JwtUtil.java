package live.databo3.front.util;

import live.databo3.front.auth.exception.InvalidTokenException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;

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
            Key key = new SecretKeySpec(secret.getBytes(), SignatureAlgorithm.HS256.getJcaName());
            Jws<Claims> claimsJws = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return claimsJws.getBody();

        } catch (SignatureException | MalformedJwtException e) {
            throw new InvalidTokenException("유효하지 않는 토큰입니다");
        } catch (ExpiredJwtException e) {
            throw new InvalidTokenException("만료된 토큰입니다");
        }
    }
}
