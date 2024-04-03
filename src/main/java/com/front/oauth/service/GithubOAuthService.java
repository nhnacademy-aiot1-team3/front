package com.front.oauth.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.front.member.dto.ResponseDto;
import com.front.member.dto.ResponseHeaderDto;
import com.front.member.dto.TokenResponseDto;
import com.front.oauth.adaptor.OAuthAdaptor;
import com.front.oauth.properties.GithubOAuthProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * OAuth GithubService
 *
 * @author 양현성
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class GithubOAuthService implements OAuthService {
    private final GithubOAuthProperties githubOAuthProperties;
    private final OAuthAdaptor oauthAdaptor;


    public String makeLoginUrl() {
        return UriComponentsBuilder
                .fromUriString(githubOAuthProperties.getAuthorizationUri())
                .queryParam("client_id", githubOAuthProperties.getClientId())
                .build().toUriString();
    }


    @Override
    public ResponseDto<ResponseHeaderDto, TokenResponseDto> doOAuthLogin(String domain, String code) {
        return oauthAdaptor.doOAuthLogin(domain, code).getBody();
    }

}
