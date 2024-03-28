package com.front.member.service.impl;

import com.front.member.adaptor.MemberAdaptor;
import com.front.member.dto.MemberRequestDto;
import com.front.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberAdaptor memberAdaptor;
    @Override
    public Optional<String> doLogin(MemberRequestDto memberRequestDto) {
        ResponseEntity<Void> answer = memberAdaptor.doLogin(memberRequestDto);
        HttpHeaders header = answer.getHeaders();
        return header.get("Authorization").stream().findFirst();
    }
}
