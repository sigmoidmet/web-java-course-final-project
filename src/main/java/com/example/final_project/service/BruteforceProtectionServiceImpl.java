package com.example.final_project.service;

import com.example.final_project.exception.DisabledClientTriedToLoginException;
import com.example.final_project.model.Client;
import com.example.final_project.repository.ClientRepository;
import lombok.extern.log4j.Log4j2;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Log4j2
public class BruteforceProtectionServiceImpl implements BruteforceProtectionService {

    private final ClientRepository clientRepository;

    private final int maxFailedLogins;

    public BruteforceProtectionServiceImpl(ClientRepository clientRepository,
                                           @Value("${authentication.max-failed-logins}") int maxFailedLogins) {
        this.clientRepository = clientRepository;
        this.maxFailedLogins = maxFailedLogins;
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void registerLoginFailure(String email) {
        clientRepository.findByEmail(email)
                .ifPresent(this::incrementFailedLoginAttemptsToClient);
    }

    private void incrementFailedLoginAttemptsToClient(Client client) {
        if (client.isDisabled()) {
            throw new DisabledClientTriedToLoginException("Client " + client.getEmail() + " is disabled and can't try to login anymore.");
        }

        client.setFailedLoginAttempts(client.getFailedLoginAttempts() + 1);

        if (client.getFailedLoginAttempts() > maxFailedLogins) {
            log.info("user {} was disabled due to too many failed login attempts.", client.getEmail());
            client.setFailedLoginAttempts(0);
            client.setDisabled(true);
        }

        clientRepository.save(client);
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void registerLoginSuccess(String email) {
        clientRepository.findByEmail(email)
                .ifPresent(this::nullifyLoginAttemptsToClient);
    }

    private void nullifyLoginAttemptsToClient(Client client) {
        client.setFailedLoginAttempts(0);
        clientRepository.save(client);
    }
}
