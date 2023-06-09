package br.pucpr.simuladorpkce.OIDCAuth;

import br.pucpr.simuladorpkce.OIDCAuth.dto.GoogleOIDCExchangeResponse;
import br.pucpr.simuladorpkce.lib.error.exceptions.ApiException;
import br.pucpr.simuladorpkce.lib.security.ApiCredentialsSettings;
import br.pucpr.simuladorpkce.lib.utils.OIDCUtils;
import br.pucpr.simuladorpkce.users.User;
import br.pucpr.simuladorpkce.users.UsersService;
import com.auth0.jwk.*;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.interfaces.Claim;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.MalformedURLException;
import java.net.URL;
import java.security.interfaces.RSAPublicKey;
import java.util.HashMap;
import java.util.Map;

@Service
public class OIDCAuthService {
    private ApiCredentialsSettings apiCredentialsSettings;
    private OIDCUtils utils;
    private UsersService usersService;

    public OIDCAuthService(ApiCredentialsSettings apiCredentialsSettings, OIDCUtils utils, UsersService usersService) {
        this.apiCredentialsSettings = apiCredentialsSettings;
        this.utils = utils;
        this.usersService = usersService;
    }

    /**
     * @param code auth code
     * troca o auth code por um access token e ID token e faz a validação
     * @return retorna o email do usuário autenticado, null caso a operação fracasse
     *
     * utilizado para api do google
     */
    public User exchange(String code){
        RestTemplate template = new RestTemplate();

        var body = new HashMap<String, String>();
        body.put("code", code);
        body.put("client_id", apiCredentialsSettings.getGoogleClientId());
        body.put("client_secret", apiCredentialsSettings.getGoogleClientSecret());
        body.put("redirect_uri", apiCredentialsSettings.getRedirectUri());
        body.put("grant_type", "authorization_code");

        var resp = template.exchange("https://oauth2.googleapis.com/token",
                HttpMethod.POST,
                new HttpEntity<>(body, null),
                GoogleOIDCExchangeResponse.class);

        GoogleOIDCExchangeResponse bodyResp = resp.getBody();

        return registerUserFromClaims(validateIdToken(bodyResp.getId_token()));
    }

    /**
     * @return retorna null se o token não for validado, caso contrário, retorna os claims
     */
    private Map<String, Claim> validateIdToken(String idToken) {
        var jwt = JWT.decode(idToken);
        try {
            JwkProvider provider;
            if(jwt.getClaim("iss").asString().contains("microsoft"))
                provider = new UrlJwkProvider(new URL(apiCredentialsSettings.getMicrosoftPubKeysUrl()));
            else
                provider = new UrlJwkProvider(new URL(apiCredentialsSettings.getGooglePubKeysUrl()));

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

    /**
     * @return valida os tokens obtidos através de plataformas que usam implicit flow (Microsoft) e cadastra o user na
     * base de dados
     */
    public User implicitFlowValidation(String idToken){
        return registerUserFromClaims(validateIdToken(idToken));
    }

    @Transactional
    public User registerUserFromClaims(Map<String, Claim> claims){
        if(claims == null){
            throw new ApiException("Failed to validate id token", HttpStatus.UNAUTHORIZED);
        }

        //verificar caso o usuário já tenha um login na plataforma
        var emailMatch = usersService.findByEmail(claims.get("email").asString());
        if(emailMatch != null) return emailMatch;

        return usersService.save(new User(claims.get("email").asString(), claims.get("name").asString()));
    }
}
