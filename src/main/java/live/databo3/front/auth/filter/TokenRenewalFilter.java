package live.databo3.front.auth.filter;


import live.databo3.front.auth.adaptor.AuthAdaptor;
import live.databo3.front.member.dto.ResponseDto;
import live.databo3.front.member.dto.ResponseHeaderDto;
import live.databo3.front.member.dto.TokenResponseDto;
import live.databo3.front.util.CookieUtil;
import live.databo3.front.util.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
public class TokenRenewalFilter extends OncePerRequestFilter {
    private final AuthAdaptor authAdaptor;
    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain){
        try {
            log.info("tokenRenewalFilter {}", request.getRequestURI());
            Cookie accessTokenCookie = CookieUtil.findCookie(request, "access_token");
            if (Objects.isNull(accessTokenCookie)) {
                log.error("no access_token cookie found");
                filterChain.doFilter(request, response);
                return;
            }
            Claims claims = jwtUtil.parseClaims(accessTokenCookie.getValue());

            Long exp = Long.parseLong(claims.get("exp").toString());

            Instant expireTime = Instant.ofEpochSecond(exp);
            Instant now = Instant.now();

            Long remainMinutes = Duration.between(now, expireTime).toMinutes();

            if (now.isBefore(expireTime) && remainMinutes <= 15) {
                Cookie refreshTokenCookie = CookieUtil.findCookie(request, "refresh_token");
                if (Objects.isNull(refreshTokenCookie)) {
                    //TODO 쿠키에 리프레시 토큰 존재하지 않을때 로직
                }
                ResponseDto<ResponseHeaderDto, TokenResponseDto> responseDto = authAdaptor.tokenReIssue(refreshTokenCookie.getValue()).getBody();
                //TODO 재발급 이후 쿠키에 다시 넣는 로직
                log.info("{}", responseDto);
            } else if (now.isAfter(expireTime)) {
                    //TODO 엑세스 토큰 만료시 로직
            }
//            request.setAttribute(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);
            filterChain.doFilter(request, response);

        } catch (Exception e) {
            //TODO 에러 구분 세세하게 나눠서 각 에러마다 처리하는 로직
            log.error("tokenRenewalFilter error", e);
        } finally {
            log.info("end");
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String[] excludePath = {
                "/login",
                "/pre_login/.*",
                "/oauth/.*",
                "/register",
                "/searchPassword",
                "/static/.*"
        };
        Set<Pattern> excludePattern = Arrays.stream(excludePath)
                .map(path -> Pattern.compile(path))
                .collect(Collectors.toSet());

        String path = request.getRequestURI();

        return excludePattern.stream().anyMatch(pattern -> pattern.matcher(path).matches());
    }
}
