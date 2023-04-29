package br.pucpr.simuladorpkce.authTokens;

import br.pucpr.simuladorpkce.authTokens.enums.AuthTokenType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "authtokens")
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class AuthToken {
    @Id
    @GeneratedValue
    private UUID id;
    @Enumerated(EnumType.STRING)
    private AuthTokenType tokenType;
    @Column(unique = true)
    private String tokenValue;

    public AuthToken(AuthTokenType tokenType, String tokenValue) {
        this.tokenType = tokenType;
        this.tokenValue = tokenValue;
    }
}
