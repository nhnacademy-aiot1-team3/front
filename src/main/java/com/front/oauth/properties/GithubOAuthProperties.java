package com.front.oauth.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "oauth.github")
public class GithubOAuthProperties {
    private String clientId;
    private String secret;
    private String redirectUrl;
}
