package com.front.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class TokenResponseDto {
    private String accessToken;
    private String refreshToken;
    private Long accessTokenExpireTime;
    private Long refreshTokenExpireTime;
}