package com.front.oauth.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
/**
 * OAuth에 필요한 Payco 설정
 *
 * @author 양현성
 */
@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "oauth.payco")
public class PaycoOAuthProperties {
    private String clientId;
    private String secret;
    private String redirectUrl;
    private String authorizationUri;

}
