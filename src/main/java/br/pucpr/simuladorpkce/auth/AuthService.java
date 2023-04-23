package br.pucpr.simuladorpkce.auth;

import br.pucpr.simuladorpkce.lib.utils.PKCEUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.*;

@Service
public class AuthService {
    private final PKCEUtils utils;

    public AuthService(PKCEUtils utils) {
        this.utils = utils;
    }

    private final static UUID[] registeredIds = {
            UUID.fromString("6d4c15aa-45d2-4792-8747-99dfb581eebb"),
            UUID.fromString("a515091f-9a41-495b-9b79-13339062d44a"),
            UUID.fromString("0dd7070c-2dff-446d-ad73-e9a78b572bd4"),
            UUID.fromString("2263b5d7-8e19-4ef4-9c78-651efb6c8006")
    };

    // <authcode, challenge>
    private final static Map<String, String> challenges = new HashMap<>();

    public String generateAuthorizationCode(String clientId, String challenge){
        if(Arrays.stream(registeredIds).noneMatch(id -> id.equals(UUID.fromString(clientId))))
            throw new HttpClientErrorException(HttpStatus.FORBIDDEN);

        var code = utils.generateRandomString(32);
        challenges.put(code, challenge);

        return code;
    }
}
