package com.example.final_project;

import com.example.final_project.config.properties.PasswordResetProperties;
import com.example.final_project.exception.InvalidPasswordResetRequest;
import com.example.final_project.exception.NoSuchEntityException;
import com.example.final_project.model.Client;
import com.example.final_project.model.PasswordResetRequest;
import com.example.final_project.repository.ClientRepository;
import com.example.final_project.repository.PasswordResetRequestRepository;
import com.example.final_project.service.EmailMessageSender;
import com.example.final_project.service.PasswordResetRequestServiceImpl;
import com.example.final_project.service.UrlProcessor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PasswordResetRequestServiceTest {

    private final Client client = Client.builder().email("mail@mail.ru").build();

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private PasswordResetRequestRepository passwordResetRequestRepository;

    @Mock
    private PasswordResetProperties passwordResetProperties;

    @Mock
    private EmailMessageSender emailMessageSender;

    @Mock
    private UrlProcessor urlProcessor;

    @InjectMocks
    private PasswordResetRequestServiceImpl passwordResetRequestService;

    @Test
    void createNewRequestAndNotifyClientWhenClientNotExistThrowException() {
        when(clientRepository.findByEmail(client.getEmail())).thenReturn(Optional.empty());

        assertThrows(NoSuchEntityException.class,
                     () -> passwordResetRequestService.createNewRequestAndNotifyClient(client.getEmail()));
    }

    @Test
    void findClientByPasswordResetRequestToken() {
        when(passwordResetRequestRepository.findById(any())).thenReturn(Optional.of(
                new PasswordResetRequest(
                        "123",
                        client,
                        LocalDateTime.now().plus(Duration.ofDays(12))
                )
        ));

        assertEquals(client, passwordResetRequestService.findClientByPasswordResetRequestToken("13"));
    }

    @Test
    void findClientByPasswordResetRequestTokenWhenNotValidThrowException() {
        when(passwordResetRequestRepository.findById(any())).thenReturn(Optional.of(
                new PasswordResetRequest(
                        "123",
                        client,
                        LocalDateTime.now().minus(Duration.ofDays(12))
                )
        ));

        assertThrows(InvalidPasswordResetRequest.class,
                     () -> passwordResetRequestService.findClientByPasswordResetRequestToken("13"));
    }

    @Test
    void findClientByPasswordResetRequestTokenWhenEmptyThrowException() {
        when(passwordResetRequestRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(InvalidPasswordResetRequest.class,
                     () -> passwordResetRequestService.findClientByPasswordResetRequestToken("13"));
    }
}
