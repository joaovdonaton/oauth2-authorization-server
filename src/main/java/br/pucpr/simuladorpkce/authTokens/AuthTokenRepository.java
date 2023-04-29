package br.pucpr.simuladorpkce.authTokens;

import br.pucpr.simuladorpkce.authTokens.enums.AuthTokenType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AuthTokenRepository extends JpaRepository<AuthToken, UUID> {
    boolean existsAuthTokenByTokenValueAndTokenType(String value, AuthTokenType tokenType);
}
