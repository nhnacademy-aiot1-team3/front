package live.databo3.front.auth.filter;

import live.databo3.front.util.CookieUtil;
import live.databo3.front.util.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("jwtAuthenticationFilter {}", request.getRequestURI());

        try {
            Cookie accessTokenCookie = CookieUtil.findCookie(request, "access_token");
            if (Objects.isNull(accessTokenCookie)) {
                log.error("no access_token cookie found");
                filterChain.doFilter(request, response);
                return;
            }

            Claims claims = jwtUtil.parseClaims(accessTokenCookie.getValue());

            String memberId = claims.get("memberId", String.class);
            String memberEmail = claims.get("memberEmail", String.class);
            List<Map<String, String>> roles = claims.get("roles", List.class);

            List<SimpleGrantedAuthority> authorities = roles.stream()
                    .map(index -> new SimpleGrantedAuthority(index.get("authority")))
                    .collect(Collectors.toList());


            SecurityContext context = SecurityContextHolder.getContext();
            context.setAuthentication(
                    new UsernamePasswordAuthenticationToken(
                            memberId,
                            null,
                            authorities
                    )
            );
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        } finally {
            SecurityContextHolder.clearContext();
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String[] excludePath = {
                "/login",
                "/oauth/.*",
                "/css/.*",
                "/js/.*",
                "/static/.*",
                "/favicon.ico"
        };
        Set<Pattern> excludePattern = Arrays.stream(excludePath)
                .map(path -> Pattern.compile(path))
                .collect(Collectors.toSet());

        String path = request.getRequestURI();

        return excludePattern.stream().anyMatch(pattern -> pattern.matcher(path).matches());
    }
}
