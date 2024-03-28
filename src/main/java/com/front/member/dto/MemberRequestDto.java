package com.front.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class MemberRequestDto {
    private String id;
    private String password;
}
