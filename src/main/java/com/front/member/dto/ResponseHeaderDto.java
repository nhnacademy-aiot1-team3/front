package com.front.member.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 응답 헤더가 resultCode와 resultMessage로 구성되어있다
 * @author 이지현
 * @version 1.0.0
 */
@Getter
@NoArgsConstructor
@ToString
public class ResponseHeaderDto {
    private Long resultCode;
    private String resultMessage;
}