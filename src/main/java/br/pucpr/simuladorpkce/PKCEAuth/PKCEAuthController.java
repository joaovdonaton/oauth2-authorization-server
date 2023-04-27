package br.pucpr.simuladorpkce.PKCEAuth;

import br.pucpr.simuladorpkce.PKCEAuth.dto.AccessTokenResponseDTO;
import br.pucpr.simuladorpkce.PKCEAuth.dto.AuthCodeResponseDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping("/pkce/")
public class PKCEAuthController {
    private final PKCEAuthService service;

    public PKCEAuthController(PKCEAuthService service) {
        this.service = service;
    }

    @GetMapping("/authorize")
    public AuthCodeResponseDTO authorize(@RequestParam String clientId, @RequestParam String challenge){
        return new AuthCodeResponseDTO(service.generateAuthorizationCode(clientId, challenge), "1800");
    }

    @GetMapping("/exchange")
    public AccessTokenResponseDTO exchange(@RequestParam String code, @RequestParam String verifier) throws NoSuchAlgorithmException {
        return new AccessTokenResponseDTO(service.generateAccessToken(code, verifier));
    }
}
