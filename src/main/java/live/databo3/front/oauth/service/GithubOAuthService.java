package live.databo3.front.oauth.service;

import live.databo3.front.member.dto.ResponseDto;
import live.databo3.front.member.dto.ResponseHeaderDto;
import live.databo3.front.member.dto.TokenResponseDto;
import live.databo3.front.oauth.adaptor.OAuthAdaptor;
import live.databo3.front.oauth.properties.GithubOAuthProperties;
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
