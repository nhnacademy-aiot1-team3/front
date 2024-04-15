package com.front.oauth.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * OAuth에 필요한 Github 설정
 *
 * @author 양현성
 */
@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "oauth.github")
public class GithubOAuthProperties {
    private String clientId;
    private String secret;
    private String redirectUri;
    private String authorizationUri;
}
