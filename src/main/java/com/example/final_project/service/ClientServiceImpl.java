package com.example.final_project.service;

import com.example.final_project.exception.ExistingUserException;
import com.example.final_project.exception.NoSuchEntityException;
import com.example.final_project.model.Client;
import com.example.final_project.repository.ClientRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public Client register(Client client) {
        validateNewClientFields(client);

        client.setPassword(passwordEncoder.encode(client.getPassword()));
        return clientRepository.save(client);
    }

    private void validateNewClientFields(Client client) {
        if (clientRepository.existsByEmail(client.getEmail())) {
            throw new ExistingUserException("The user with email " + client.getEmail() + " already exists.");
        }
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void resetPassword(Long clientId, String newPassword) {
        Client client = getById(clientId);
        client.setPassword(passwordEncoder.encode(newPassword));

        clientRepository.save(client);
    }

    private Client getById(Long clientId) {
        return clientRepository.findById(clientId)
                .orElseThrow(() -> new NoSuchEntityException("There is no client by id " + clientId));
    }
}
