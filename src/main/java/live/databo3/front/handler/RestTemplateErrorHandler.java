package live.databo3.front.handler;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import live.databo3.front.member.dto.ResponseDto;
import live.databo3.front.member.dto.ResponseHeaderDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;
import java.util.stream.Collectors;


/**
 * RestTemplate에서 발생한 에러를 이곳에서 잡아서 에러 종류에 따라 각각의 method를 실행한다
 * @author 나채현
 * @version 1.0.2
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class RestTemplateErrorHandler implements ResponseErrorHandler {
    private final ObjectMapper objectMapper;
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
        ResponseDto<ResponseHeaderDto,Object> responseDto = objectMapper.readValue(
                response.getBody(),
                new TypeReference<>() {
        });
        String resultMessage = responseDto.getHeader().getResultMessage();
        if (response.getStatusCode().series() == HttpStatus.Series.CLIENT_ERROR) {
            if (response.getStatusCode() == HttpStatus.BAD_REQUEST) {
                throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, resultMessage);
            }
            if (response.getStatusCode() == HttpStatus.UNAUTHORIZED) {
                throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED, resultMessage);
            }
            if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new HttpClientErrorException(HttpStatus.NOT_FOUND, resultMessage);
            }
        }
    }
}
