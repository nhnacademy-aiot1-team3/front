package live.databo3.front.member.dto;

import lombok.*;

/**
 * api 응답을 위한 dto로 header와 body type을 각각 지정
 * @param <T> 응답 헤더의 type을 지정
 * @param <V> 응답 바디의 type을 지정
 * @author 이지현
 * @version 1.0.0
 */
@Getter
@NoArgsConstructor
@ToString
public class ResponseDto<T,V> {
    private T header;
    private V body;

    @Builder
    public ResponseDto (T header, V body) {
        this.header = header;
        this.body = body;
    }
}