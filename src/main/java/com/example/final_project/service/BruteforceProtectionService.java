package com.example.final_project.service;

public interface BruteforceProtectionService {

    void registerLoginFailure(String email);

    void registerLoginSuccess(String email);
}
