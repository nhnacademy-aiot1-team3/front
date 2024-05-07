package live.databo3.front.auth.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import live.databo3.front.auth.dto.JwtPayloadDto;
import live.databo3.front.util.CookieUtil;
import live.databo3.front.util.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
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
    private final ObjectMapper objectMapper;
    private final String[] excludePath;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain){
        log.info("jwtAuthenticationFilter {}", request.getRequestURI());
        try {
            Object attribute = request.getAttribute(HttpHeaders.AUTHORIZATION);
            if (Objects.isNull(attribute)) {
                log.error("no authorization header found in request");
                filterChain.doFilter(request, response);
                return;
            }

            String accessToken = attribute.toString();

            JwtPayloadDto jwtPayloadDto = objectMapper.readValue(new String(Base64.getDecoder().decode(accessToken.split("\\.")[1])), JwtPayloadDto.class);

            String memberId = jwtPayloadDto.getMemberId();
            String memberEmail = jwtPayloadDto.getMemberEmail();

            List<SimpleGrantedAuthority> authorities = jwtPayloadDto.getRoles().stream()
                    .map(authenticationRoleDto -> new SimpleGrantedAuthority(authenticationRoleDto.getAuthority()))
                    .collect(Collectors.toList());

            log.info("memberId: {}, memberEmail: {}, authorities: {}", memberId, memberEmail, authorities);
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
//            log.error(e.getMessage());
        } finally {
            SecurityContextHolder.clearContext();
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        Set<Pattern> excludePattern = Arrays.stream(excludePath)
                .map(path -> Pattern.compile(path))
                .collect(Collectors.toSet());

        String path = request.getRequestURI();

        return excludePattern.stream().anyMatch(pattern -> pattern.matcher(path).matches());
    }
}
