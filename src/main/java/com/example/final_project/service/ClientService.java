package com.example.final_project.service;

import com.example.final_project.model.Client;

public interface ClientService {

    Client register(Client client);

    void resetPassword(Long clientId, String newPassword);
}
