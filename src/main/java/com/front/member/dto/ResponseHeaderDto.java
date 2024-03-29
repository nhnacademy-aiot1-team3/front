package com.front.member.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
public class ResponseHeaderDto {
    private Long resultCode;
    private String resultMessage;
}