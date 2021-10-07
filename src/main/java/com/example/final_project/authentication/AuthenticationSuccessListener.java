package com.example.final_project.authentication;

import com.example.final_project.service.BruteforceProtectionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Log4j2
public class AuthenticationSuccessListener implements ApplicationListener<AuthenticationSuccessEvent> {

    private final BruteforceProtectionService bruteforceProtectionService;

    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent authenticationSuccessEvent) {
        String email = authenticationSuccessEvent.getAuthentication().getName();
        log.info("login successful for user {} ", email);
        bruteforceProtectionService.registerLoginSuccess(email);
    }
}
