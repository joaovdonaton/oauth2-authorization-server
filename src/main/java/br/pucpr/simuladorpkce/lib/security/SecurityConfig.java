package br.pucpr.simuladorpkce.lib.security;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;

@Configuration
@EnableMethodSecurity(securedEnabled = true)
@SecurityScheme(
        name = "auth",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "bearer",
        scheme = "bearer"
)
public class SecurityConfig {
    private final AccessTokenFilter filter;

    public SecurityConfig(AccessTokenFilter filter) {
        this.filter = filter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity security) throws Exception{

        return security.headers().frameOptions().disable().and()
                .cors().and().csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .exceptionHandling()
                .authenticationEntryPoint((req, res, ex) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED, ex.getMessage())).and()
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers("/swagger-ui/**").permitAll()
                        .requestMatchers("/export/**").permitAll()
                        .requestMatchers("/pkce/**").permitAll()
                        .requestMatchers("/oidc/**").permitAll()
                        .requestMatchers("/codegrant/**").permitAll()
                        .requestMatchers(toH2Console()).permitAll()
                        .anyRequest().authenticated())
                .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
