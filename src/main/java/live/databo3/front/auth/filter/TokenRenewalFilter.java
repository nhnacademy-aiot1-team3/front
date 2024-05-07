package live.databo3.front.auth.filter;


import com.fasterxml.jackson.databind.ObjectMapper;
import live.databo3.front.auth.adaptor.AuthAdaptor;
import live.databo3.front.auth.dto.JwtPayloadDto;
import live.databo3.front.auth.exception.MissingRefreshTokenException;
import live.databo3.front.auth.exception.TokenExpiredException;
import live.databo3.front.member.dto.ResponseDto;
import live.databo3.front.member.dto.ResponseHeaderDto;
import live.databo3.front.member.dto.TokenResponseDto;
import live.databo3.front.util.CookieUtil;
import live.databo3.front.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.Base64;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
public class TokenRenewalFilter extends OncePerRequestFilter {
    private final AuthAdaptor authAdaptor;
    private final JwtUtil jwtUtil;
    private final ObjectMapper objectMapper;
    private final String[] excludePath;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException, MissingRefreshTokenException {

        log.info("tokenRenewalFilter {}", request.getRequestURI());
        Cookie accessTokenCookie = CookieUtil.findCookie(request, "access_token");

        if (Objects.isNull(accessTokenCookie)) {
            log.error("no access_token cookie found");
            filterChain.doFilter(request, response);
            return;
        }
        JwtPayloadDto jwtPayloadDto = objectMapper.readValue(new String(Base64.getDecoder().decode(accessTokenCookie.getValue().split("\\.")[1])), JwtPayloadDto.class);


        Long exp = jwtPayloadDto.getExp();

        Instant expireTime = Instant.ofEpochSecond(exp);
        Instant now = Instant.now();

        Long remainMinutes = Duration.between(now, expireTime).toMinutes();

        if (now.isBefore(expireTime) && remainMinutes <= 15) {
            Cookie refreshTokenCookie = CookieUtil.findCookie(request, "refresh_token");
            if (Objects.isNull(refreshTokenCookie)) {
                throw new MissingRefreshTokenException();
            }
            ResponseDto<ResponseHeaderDto, TokenResponseDto> responseDto = authAdaptor.tokenReIssue(refreshTokenCookie.getValue()).getBody();


            String accessToken = responseDto.getBody().getAccessToken();
            String refreshToken = responseDto.getBody().getRefreshToken();


            accessTokenCookie.setValue(accessToken);
            refreshTokenCookie.setValue(refreshToken);

            log.info("{}", responseDto);
        } else if (now.isAfter(expireTime)) {
            throw new TokenExpiredException();
        }
        request.setAttribute(HttpHeaders.AUTHORIZATION, accessTokenCookie.getValue());
        filterChain.doFilter(request, response);

    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {

        String[] excludePath = {
                "/logout",
                "/assets/.*",
                "/error"
        };

        Set<Pattern> excludePattern = Arrays.stream(excludePath)
                .map(path -> Pattern.compile(path))
                .collect(Collectors.toSet());

        String path = request.getRequestURI();

        return excludePattern.stream().anyMatch(pattern -> pattern.matcher(path).matches());
    }
}
