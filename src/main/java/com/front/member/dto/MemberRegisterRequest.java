package com.front.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class MemberRegisterRequest {
    private String id;
    private String password;
    private String email;
}
