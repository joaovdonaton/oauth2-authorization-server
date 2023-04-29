package br.pucpr.simuladorpkce.lib.security;

import br.pucpr.simuladorpkce.authTokens.AuthToken;
import br.pucpr.simuladorpkce.authTokens.AuthTokenService;
import br.pucpr.simuladorpkce.authTokens.enums.AuthTokenType;
import br.pucpr.simuladorpkce.users.UsersService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class AccessTokenFilter extends GenericFilterBean {
    private final AuthTokenService authTokenService;

    public AccessTokenFilter(AuthTokenService authTokenService) {
        this.authTokenService = authTokenService;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        var req = (HttpServletRequest) servletRequest;
        AuthToken token = null;
        try {
            token = authTokenService.findByValueAndType(req.getHeader("Authorization").split(" ")[1], AuthTokenType.ACCESS);

            if (token == null) {
                filterChain.doFilter(servletRequest, servletResponse);
                return;
            }

            List<GrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
            var auth = new UsernamePasswordAuthenticationToken(token.getOwnerClientId(), token.getTokenValue(), authorities);
            SecurityContextHolder.getContext().setAuthentication(auth);
            filterChain.doFilter(servletRequest, servletResponse);
        }
        catch(Exception e){
            System.out.println("Filter failure: " + e.getMessage());
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }
}
