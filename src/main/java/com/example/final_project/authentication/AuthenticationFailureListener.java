package com.example.final_project.authentication;

import com.example.final_project.service.BruteforceProtectionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Log4j2
public class AuthenticationFailureListener implements ApplicationListener<AuthenticationFailureBadCredentialsEvent> {

    private final BruteforceProtectionService bruteforceProtectionService;

    @Override
    public void onApplicationEvent(AuthenticationFailureBadCredentialsEvent authenticationFailureBadCredentialsEvent) {
        String email = authenticationFailureBadCredentialsEvent.getAuthentication().getName();
        log.info("login failed for user {} ", email);
        bruteforceProtectionService.registerLoginFailure(email);
    }
}
