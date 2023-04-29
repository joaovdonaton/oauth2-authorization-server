package br.pucpr.simuladorpkce.lib.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("/apiCredentials.properties")
@ConfigurationProperties("credentials")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiCredentialsSettings {
    private String googleClientId;
    private String googleClientSecret;
    private String redirectUri;
    private String googlePubKeysUrl;
    private String microsoftPubKeysUrl;
}
