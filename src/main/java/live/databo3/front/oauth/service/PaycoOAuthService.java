package live.databo3.front.oauth.service;

import live.databo3.front.member.dto.ResponseDto;
import live.databo3.front.member.dto.ResponseHeaderDto;
import live.databo3.front.member.dto.TokenResponseDto;
import live.databo3.front.oauth.adaptor.OAuthAdaptor;
import live.databo3.front.oauth.properties.PaycoOAuthProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaycoOAuthService implements OAuthService {
    private final PaycoOAuthProperties paycoOAuthProperties;
    private final OAuthAdaptor oauthAdaptor;

    @Override
    public String makeLoginUrl() {
        return UriComponentsBuilder.newInstance()
                .scheme("https")
                .host("id.payco.com")
                .path("oauth2.0/authorize")
                .queryParam("response_type", "code")
                .queryParam("client_id", paycoOAuthProperties.getClientId())
                .queryParam("redirect_uri", paycoOAuthProperties.getRedirectUrl())
                .queryParam("serviceProviderCode", "FRIENDS")
                .queryParam("userLocale", "ko_KR")
                .build().toUriString();
    }

    @Override
    public ResponseDto<ResponseHeaderDto, TokenResponseDto> doOAuthLogin(String domain, String code) {
        return oauthAdaptor.doOAuthLogin(domain, code).getBody();
    }
}
