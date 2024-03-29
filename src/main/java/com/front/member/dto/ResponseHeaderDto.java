package com.front.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResponseHeaderDto {
    private Long resultCode;
    private String resultMessage;
}