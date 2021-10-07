package com.example.final_project.service;

import com.example.final_project.model.Client;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface ClientService {

    Client register(Client client);

    boolean isPasswordTheSame(Long clientId, String oldPassword);

    void resetPassword(Long clientId, String newPassword);
}
