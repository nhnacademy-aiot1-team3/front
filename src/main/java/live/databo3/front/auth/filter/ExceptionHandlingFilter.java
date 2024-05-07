package live.databo3.front.auth.filter;

import live.databo3.front.auth.exception.MissingRefreshTokenException;
import live.databo3.front.auth.exception.TokenExpiredException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
public class ExceptionHandlingFilter extends OncePerRequestFilter {
    private final String[] excludePath;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (MissingRefreshTokenException | TokenExpiredException e) {
            log.info("exceptionHandlingFilter");
            response.sendRedirect("/login");
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
