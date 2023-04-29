package br.pucpr.simuladorpkce.PKCEAuth;

import br.pucpr.simuladorpkce.authTokens.AuthToken;
import br.pucpr.simuladorpkce.authTokens.AuthTokenService;
import br.pucpr.simuladorpkce.authTokens.enums.AuthTokenType;
import br.pucpr.simuladorpkce.lib.error.exceptions.ApiException;
import br.pucpr.simuladorpkce.lib.utils.SecurityUtils;
import br.pucpr.simuladorpkce.users.UsersService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

@Service
public class PKCEAuthService {
    private final SecurityUtils utils;
    private final UsersService usersService;
    private final AuthTokenService authTokenService;

    public PKCEAuthService(SecurityUtils utils, UsersService usersService, AuthTokenService authTokenService) {
        this.utils = utils;
        this.usersService = usersService;
        this.authTokenService = authTokenService;
    }

    // <authcode, challenge>
    private final static Map<String, String> challenges = new HashMap<>();

    public String generateAuthorizationCode(String clientId, String challenge){
        if(!usersService.existsById(UUID.fromString(clientId)))
            throw new ApiException("Client not registered", HttpStatus.UNAUTHORIZED);

        var code = utils.generateRandomString(32);
        challenges.put(code, challenge);

        authTokenService.save(new AuthToken(AuthTokenType.AUTHORIZATION, code));

        return code;
    }

    public String generateAccessToken(String authCode, String verifier) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");

        // digest retorna hash como byte[], o método utilitário converte para uma string hexadecimal
        var hash = utils.bytesToHex(md.digest(verifier.getBytes(StandardCharsets.UTF_8)));

        // codificar string hexadecimal SEM O PADDING
        var base64Hash = Base64.getEncoder().withoutPadding().encodeToString(hash.getBytes());

        if(!base64Hash.equals(challenges.get(authCode)))
            throw new ApiException("Incorrect code verifier", HttpStatus.UNAUTHORIZED);

        var token = authTokenService.save(new AuthToken(AuthTokenType.ACCESS, utils.generateRandomString(32)));

        return token.getTokenValue();
    }
}
