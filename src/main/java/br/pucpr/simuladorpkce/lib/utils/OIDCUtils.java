package br.pucpr.simuladorpkce.lib.utils;

import br.pucpr.simuladorpkce.OIDCAuth.dto.GoogleOIDCExchangeResponse;
import br.pucpr.simuladorpkce.lib.security.ApiCredentialsSettings;
import br.pucpr.simuladorpkce.lib.utils.dto.GoogleCertDTO;
import br.pucpr.simuladorpkce.lib.utils.dto.GoogleCertsResponse;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class OIDCUtils {
    private ApiCredentialsSettings settings;

    public OIDCUtils(ApiCredentialsSettings settings) {
        this.settings = settings;
    }

//    public List<String> retrieveGooglePublicKeys(){
//        var template = new RestTemplate();
//        var resp = template.exchange(settings.getGooglePubKeysUrl(), HttpMethod.GET, null, GoogleCertsResponse.class);
//
//        return resp.getBody().getKeys().stream().map(GoogleCertDTO::getN).toList();
//    }
}
