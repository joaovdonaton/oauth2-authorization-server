package br.pucpr.simuladorpkce.OIDCAuth;

import br.pucpr.simuladorpkce.OIDCAuth.dto.GoogleOIDCExchangeResponse;
import br.pucpr.simuladorpkce.lib.security.ApiCredentialsSettings;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class OIDCAuthService {
    private ApiCredentialsSettings apiCredentialsSettings;

    public OIDCAuthService(ApiCredentialsSettings apiCredentialsSettings) {
        this.apiCredentialsSettings = apiCredentialsSettings;
    }

    /**
     * @param code auth code
     * troca o auth code por um access token e ID token e faz a validação
     */
    public void exchange(String code){
        RestTemplate template = new RestTemplate();

        var body = new HashMap<String, String>();
        body.put("code", code);
        body.put("client_id", apiCredentialsSettings.getGoogleClientId());
        body.put("client_secret", apiCredentialsSettings.getGoogleClientSecret());
        System.out.println(apiCredentialsSettings.getRedirectUri());
        body.put("redirect_uri", apiCredentialsSettings.getRedirectUri());
        body.put("grant_type", "authorization_code");

        var resp = template.exchange("https://oauth2.googleapis.com/token",
                HttpMethod.POST,
                new HttpEntity<>(body, null),
                GoogleOIDCExchangeResponse.class);

        System.out.println(resp.getBody());
    }
}
