package live.databo3.front.oauth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * 알맞은 OAuthService 리턴해주는 클래스
 *
 * @author 양현성
 */
@Component
@RequiredArgsConstructor
public class OAuthServiceResolver {
    private final GithubOAuthService githubOAuthService;
    private final PaycoOAuthService paycoOAuthService;


    public OAuthService getOAuthService(String domain) {
        if (domain.equalsIgnoreCase("github")) {
            return githubOAuthService;
        } else if (domain.equalsIgnoreCase("payco")) {
            return paycoOAuthService;
        } else {
            return null;
        }
    }

}
