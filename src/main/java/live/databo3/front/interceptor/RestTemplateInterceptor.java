package live.databo3.front.interceptor;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

public class RestTemplateInterceptor implements ClientHttpRequestInterceptor {
    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
//        HttpHeaders httpHeaders = request.getHeaders();
        ClientHttpResponse response = execution.execute(request, body);
        response.getHeaders().add("Authorization", "Bearer " + response.getHeaders().getFirst("access_token"));
        return response;
    }
}
