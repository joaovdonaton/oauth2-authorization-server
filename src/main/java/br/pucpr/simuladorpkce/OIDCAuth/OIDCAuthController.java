package br.pucpr.simuladorpkce.OIDCAuth;

import br.pucpr.simuladorpkce.OIDCAuth.dto.RegisteredUserInfoDTO;
import br.pucpr.simuladorpkce.users.User;
import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(summary = "uses authorization code to retrieve id token and register user (Google API)")
    @GetMapping("/exchange")
    public RegisteredUserInfoDTO registerUser(@RequestParam String code){
        User u = service.exchange(code);

        return new RegisteredUserInfoDTO(u.getId().toString(), u.getEmail(), u.getName());
    }

    @Operation(summary = "validates implicit flow id tokens and registers user (Microsoft API)")
    @GetMapping("/validate")
    public RegisteredUserInfoDTO validateTokenAndRegister(@RequestParam String idToken){
        User u = service.implicitFlowValidation(idToken);
        return new RegisteredUserInfoDTO(u.getId().toString(), u.getEmail(), u.getName());
    }
}
