package com.example.final_project.service;


import com.example.final_project.model.Client;

public interface PasswordResetRequestService {

    void createNewRequestAndNotifyClient(String email);

    Client findClientByPasswordResetRequestToken(String token);
}
