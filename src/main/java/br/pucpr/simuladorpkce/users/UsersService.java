package br.pucpr.simuladorpkce.users;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class UsersService {
    private UsersRepository repository;

    public UsersService(UsersRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public User save(User u){
        return repository.save(u);
    }

    @Transactional
    public User findByEmail(String email){
        return repository.findByEmail(email);
    }
}
