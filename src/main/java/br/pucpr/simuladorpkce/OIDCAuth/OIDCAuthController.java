package br.pucpr.simuladorpkce.OIDCAuth;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/oidc")
public class OIDCAuthController {
    private OIDCAuthService service;

    public OIDCAuthController(OIDCAuthService service) {
        this.service = service;
    }

    @GetMapping("/exchange")
    public void registerUser(@RequestParam String code){
        service.exchange(code);
    }
}
