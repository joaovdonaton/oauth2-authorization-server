package br.pucpr.simuladorpkce.lib.db;

import br.pucpr.simuladorpkce.users.User;
import br.pucpr.simuladorpkce.users.UsersService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Bootstrap implements CommandLineRunner {
    private UsersService usersService;
    private Logger logger = LogManager.getLogger(Bootstrap.class);

    public Bootstrap(UsersService usersService) {
        this.usersService = usersService;
    }

    @Override
    public void run(String... args) throws Exception {
        logger.info("CRIANDO USUÁRIO DE TESTE...");
        var testUser = usersService.save(new User("test@testmail.com", "Testerson Testings Testeira"));
        logger.info("USUÁRIO DE TESTE CRIADO");
        logger.info("ID DO USUÁRIO DE TESTE: " + testUser.getId());
    }
}
