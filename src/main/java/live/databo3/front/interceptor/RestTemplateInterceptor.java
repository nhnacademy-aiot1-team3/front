package live.databo3.front.interceptor;

import live.databo3.front.util.CookieUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Objects;

@Slf4j
public class RestTemplateInterceptor implements ClientHttpRequestInterceptor {
    private final HttpServletRequest httpServletRequest;

    public RestTemplateInterceptor(HttpServletRequest httpServletRequest) {
        this.httpServletRequest = httpServletRequest;
    }

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        String requestUrl = request.getURI().toString();
        if (requestUrl.contains("/auth/token/reissue")) {
            return execution.execute(request, body);
        }
        HttpHeaders headers = request.getHeaders();

        String accessToken = extractAccessTokenFromCookie(httpServletRequest);

        if (accessToken != null && !accessToken.isEmpty()) {
            headers.add("Authorization", accessToken);
        }
        return execution.execute(request, body);
    }

    private String extractAccessTokenFromCookie(HttpServletRequest request) {
        Cookie accessTokenCookie = CookieUtil.findCookie(request, "access_token");
        if (Objects.isNull(accessTokenCookie)) {
            log.error("no access_token cookie found");
            return null;
        }
        return accessTokenCookie.getValue();
    }
}
