package com.front.member.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ResponseHeaderDto {
    private Long resultCode;
    private String resultMessage;
}