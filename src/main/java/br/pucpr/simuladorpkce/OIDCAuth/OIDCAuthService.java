package br.pucpr.simuladorpkce.OIDCAuth;

import br.pucpr.simuladorpkce.OIDCAuth.dto.GoogleOIDCExchangeResponse;
import br.pucpr.simuladorpkce.lib.error.exceptions.ApiException;
import br.pucpr.simuladorpkce.lib.security.ApiCredentialsSettings;
import br.pucpr.simuladorpkce.lib.utils.OIDCUtils;
import com.auth0.jwk.*;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.interfaces.Claim;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.MalformedURLException;
import java.net.URL;
import java.security.*;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Service
public class OIDCAuthService {
    private ApiCredentialsSettings apiCredentialsSettings;
    private OIDCUtils utils;

    public OIDCAuthService(ApiCredentialsSettings apiCredentialsSettings, OIDCUtils utils) {
        this.apiCredentialsSettings = apiCredentialsSettings;
        this.utils = utils;
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

        GoogleOIDCExchangeResponse bodyResp = resp.getBody();

        var claims = validateIdToken(bodyResp.getId_token());
        if(claims == null){
            throw new ApiException("Failed to validate id token", HttpStatus.UNAUTHORIZED);
        }

        claims.entrySet().stream().forEach(c -> System.out.println(c.getKey() + " : " + c.getValue()));
    }

    /**
     * @param idToken
     * @return retorna null se o token não for validado, caso contrário, retorna os claims
     */
    private Map<String, Claim> validateIdToken(String idToken) {
        var jwt = JWT.decode(idToken);
        try {
            JwkProvider provider = new UrlJwkProvider(new URL(apiCredentialsSettings.getGooglePubKeysUrl()));
            // getKeyId retorna o kid (identificar do chave, para quando temos mais de uma chave para assinar e verificar os tokens)
            // essa chamada pega a public key correspondente
            Jwk jwk = provider.get(jwt.getKeyId());

            Algorithm algo = Algorithm.RSA256((RSAPublicKey) jwk.getPublicKey(), null);
            algo.verify(jwt);
            return jwt.getClaims();
        }
        catch(SignatureVerificationException e){
            return null;
        }
        catch (JwkException | MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
}
