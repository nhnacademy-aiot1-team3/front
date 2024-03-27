package com.front.member.service.impl;

import com.front.member.adaptor.MemberAdaptor;
import com.front.member.dto.MemberRequestDto;
import com.front.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberAdaptor frontAdaptor;
    @Override
    public void doLogin(MemberRequestDto userDto) {
        frontAdaptor.doLogin(userDto);
    }
}
