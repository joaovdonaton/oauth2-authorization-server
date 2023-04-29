package br.pucpr.simuladorpkce.CodeGrantAuth;

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
public class CodeGrantAuthService {
    private final UsersService usersService;
    private final SecurityUtils utils;

    public CodeGrantAuthService(UsersService usersService, SecurityUtils utils) {
        this.usersService = usersService;
        this.utils = utils;
    }

    private final static Map<String, String> registeredApps = new HashMap<>();

    static {
        registeredApps.put("oO1ACVLutKqc1usZKFzP4xVu0PTeX2k6", "03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4");
        registeredApps.put("O35KSFInNC1y5atLeoShOks6khsUufD6", "03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4");
        registeredApps.put("2QRdB1rog2clBsKgKaLfSi066ltuv0iL", "03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4");
    }

    public String generateAuthorizationCode(String clientId){
        if(!usersService.existsById(UUID.fromString(clientId)))
            throw new ApiException("Client not registered", HttpStatus.UNAUTHORIZED);

        return utils.generateRandomString(32);
    }

    public String generateAccessToken(String authCode, String appId, String appSecret) {
        if(registeredApps.containsKey(appId) && registeredApps.get(appId).equals(appSecret))
            return utils.generateRandomString(32);
        throw new ApiException("Failed to generate Access Token: Invalid Credentials", HttpStatus.UNAUTHORIZED);
    }
}
