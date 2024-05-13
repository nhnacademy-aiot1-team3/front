package live.databo3.front.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import live.databo3.front.handler.RestTemplateErrorHandler;
import live.databo3.front.interceptor.RestTemplateInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.time.Duration;
import java.util.Collections;

/**
 * RestTemplate 설정 클래스
 *
 * @author 양현성
 */
@Configuration
public class RestTemplateConfig {
    private final HttpServletRequest httpServletRequest;
    private final ObjectMapper objectMapper;

    @Autowired
    public RestTemplateConfig(HttpServletRequest httpServletRequest,ObjectMapper objectMapper) {
        this.httpServletRequest = httpServletRequest;
        this.objectMapper = objectMapper;
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        RestTemplate restTemplate = builder
                .errorHandler(new RestTemplateErrorHandler(objectMapper))
                .setReadTimeout(Duration.ofSeconds(10L))
                .setConnectTimeout(Duration.ofSeconds(10L))
                .build();
        restTemplate.setInterceptors(Collections.singletonList(restTemplateInterceptor()));
        return restTemplate;
    }

    @Bean
    public RestTemplateInterceptor restTemplateInterceptor() {
        return new RestTemplateInterceptor(httpServletRequest);
    }
}
