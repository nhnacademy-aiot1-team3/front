package com.front.member.dto;

import lombok.*;

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