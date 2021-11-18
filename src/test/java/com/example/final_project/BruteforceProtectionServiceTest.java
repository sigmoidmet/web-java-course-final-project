package com.example.final_project;

import com.example.final_project.exception.DisabledClientTriedToLoginException;
import com.example.final_project.model.Client;
import com.example.final_project.repository.ClientRepository;
import com.example.final_project.service.BruteforceProtectionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BruteforceProtectionServiceTest {

    private final Client client = Client.builder().email("aakutin@griddynamics.com").build();

    private final int maxFailedLogins = 2;

    @Mock
    private ClientRepository clientRepository;

    private BruteforceProtectionServiceImpl bruteforceProtectionService;

    @BeforeEach
    void setUp() {
        bruteforceProtectionService = new BruteforceProtectionServiceImpl(clientRepository, maxFailedLogins);

        when(clientRepository.findByEmail(client.getEmail())).thenReturn(Optional.of(client));
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2})
    void registerLoginFailureLessOrEqualThanMaxFailedLogins(int loginAttempts) {
        for (int i = 0; i < loginAttempts; ++i) {
            bruteforceProtectionService.registerLoginFailure(client.getEmail());
        }

        verify(clientRepository, times(loginAttempts)).save(client);
        assertEquals(loginAttempts, client.getFailedLoginAttempts());
        assertFalse(client.isDisabled());
    }

    @Test
    void registerLoginFailureMoreThanMaxFailedLogins() {
        for (int i = 0; i <= maxFailedLogins; ++i) {
            bruteforceProtectionService.registerLoginFailure(client.getEmail());
        }

        verify(clientRepository, times(3)).save(client);
        assertEquals(0, client.getFailedLoginAttempts());
        assertTrue(client.isDisabled());
    }

    @Test
    void registerLoginFailureAfterDisablingThrowException() {
        for (int i = 0; i <= maxFailedLogins; ++i) {
            bruteforceProtectionService.registerLoginFailure(client.getEmail());
        }
        assertThrows(DisabledClientTriedToLoginException.class,
                     () -> bruteforceProtectionService.registerLoginFailure(client.getEmail()));

        verify(clientRepository, times(3)).save(client);
        assertEquals(0, client.getFailedLoginAttempts());
        assertTrue(client.isDisabled());
    }

    @Test
    void registerLoginSuccess() {
        client.setFailedLoginAttempts(maxFailedLogins);
        bruteforceProtectionService.registerLoginSuccess(client.getEmail());

        verify(clientRepository, times(1)).save(client);
        assertEquals(0, client.getFailedLoginAttempts());
        assertFalse(client.isDisabled());
    }
}
