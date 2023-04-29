package br.pucpr.simuladorpkce.restrictedEndpoints;

import br.pucpr.simuladorpkce.users.UsersService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/secret")
public class RestrictedEndpointController {
//    @SecurityRequirement(name="auth")
    @SecurityRequirement(name="auth")
    @GetMapping
    public String getSecretData(){
        return "ENDPOINT SECRET! Current user id: " +  SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
