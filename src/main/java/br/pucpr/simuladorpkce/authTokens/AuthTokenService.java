package br.pucpr.simuladorpkce.authTokens;

import br.pucpr.simuladorpkce.authTokens.enums.AuthTokenType;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class AuthTokenService {
    private final AuthTokenRepository repository;

    public AuthTokenService(AuthTokenRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public AuthToken save(AuthToken at){
        return repository.save(at);
    }

    public boolean existsByTokenValueAndType(String value, AuthTokenType type){
        return repository.existsAuthTokenByTokenValueAndTokenType(value, type);
    }
}
