package br.pucpr.simuladorpkce.CodeGrantAuth;

import br.pucpr.simuladorpkce.CodeGrantAuth.dto.AccessTokenResponseDTO;
import br.pucpr.simuladorpkce.CodeGrantAuth.dto.AuthCodeResponseDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping("/codegrant/")
public class CodeGrantAuthController {
    private final CodeGrantAuthService service;

    public CodeGrantAuthController(CodeGrantAuthService service) {
        this.service = service;
    }

    @GetMapping("/authorize")
    public AuthCodeResponseDTO authorize(@RequestParam String clientId){
        return new AuthCodeResponseDTO(service.generateAuthorizationCode(clientId), "1800");
    }

    /**
     * @param code authorization code do endpoint authorize
     * @param appId client id do servidor
     * @param appSecret client secret do servidor
     */
    @GetMapping("/exchange")
    public AccessTokenResponseDTO exchange(@RequestParam String code, @RequestParam String appId, @RequestParam String appSecret) {
        return new AccessTokenResponseDTO(service.generateAccessToken(code, appId, appSecret));
    }
}
