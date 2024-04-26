package live.databo3.front.handler;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Collectors;


/**
 * RestTemplate에서 발생한 에러를 이곳에서 잡아서 에러 종류에 따라 각각의 method를 실행한다
 * @author 나채현
 * @version 1.0.2
 */
@Component
public class RestTemplateErrorHandler implements ResponseErrorHandler {

    /**
     * RestTeamplte에서 Server Exception(5xx), Client Exception(4xx)이 발생하는지 확인한다
     * @return Exception 발생시 true, Exception 발생 안하면 false
     */
    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        return response.getStatusCode().series() == HttpStatus.Series.CLIENT_ERROR
                || response.getStatusCode().series() == HttpStatus.Series.SERVER_ERROR;
    }

    /**
     * Exception 발생시 Client Exception인지 확인 후 status code가 BAD_REQUEST일때
     * body안에 header에 result message를 다시 HttpClientErrorException에 담음
     */
    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        if (response.getStatusCode().series() == HttpStatus.Series.CLIENT_ERROR) {
            if (response.getStatusCode() == HttpStatus.BAD_REQUEST) {
                String responseBody = new BufferedReader(new InputStreamReader(response.getBody()))
                        .lines()
                        .collect(Collectors.joining("\n"));
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(responseBody);
                JsonNode headerNode = jsonNode.get("header");
                if (headerNode != null) {
                    String resultMessage = headerNode.get("resultMessage").asText();
                    throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, resultMessage);
                }
            }
        }
    }
}
