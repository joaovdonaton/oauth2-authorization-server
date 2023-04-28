package br.pucpr.simuladorpkce.OIDCAuth;

import br.pucpr.simuladorpkce.OIDCAuth.dto.RegisteredUserInfoDTO;
import br.pucpr.simuladorpkce.users.User;
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
    public RegisteredUserInfoDTO registerUser(@RequestParam String code){
        User u = service.exchange(code);

        return new RegisteredUserInfoDTO(u.getEmail(), u.getName());
    }
}
