package com.example.final_project.service;

import com.example.final_project.config.properties.PasswordResetProperties;
import com.example.final_project.pojo.EmailMessage;
import com.example.final_project.exception.InvalidPasswordResetRequest;
import com.example.final_project.exception.NoSuchEntityException;
import com.example.final_project.infrastructure.EmailTemplate;
import com.example.final_project.model.Client;
import com.example.final_project.model.PasswordResetRequest;
import com.example.final_project.repository.ClientRepository;
import com.example.final_project.repository.PasswordResetRequestRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.example.final_project.pojo.QueryParam.param;

@Service
@RequiredArgsConstructor
public class PasswordResetRequestServiceImpl implements PasswordResetRequestService {

    private final ClientRepository clientRepository;

    private final PasswordResetRequestRepository passwordResetRequestRepository;

    private final PasswordResetProperties passwordResetProperties;

    private final EmailMessageSender emailMessageSender;

    private final UrlProcessor urlProcessor;

    @Override
    public void createNewRequestAndNotifyClient(String email) {
        Client client = findClientByEmail(email);

        PasswordResetRequest passwordResetRequest = createPasswordResetRequest(client);

        emailMessageSender.sendEmail(buildEmailMessage(email, passwordResetRequest));
    }

    private Client findClientByEmail(String email) {
        return clientRepository.findByEmail(email)
                .orElseThrow(() -> new NoSuchEntityException("There is no client with email " + email));
    }

    private PasswordResetRequest createPasswordResetRequest(Client client) {
        UUID token = generateToken();

        return passwordResetRequestRepository.save(
                new PasswordResetRequest(
                        token.toString(),
                        client,
                        LocalDateTime.now().plus(passwordResetProperties.getTokenExpirationDuration())
                )
        );
    }

    private UUID generateToken() {
        UUID token = UUID.randomUUID();
        while (passwordResetRequestRepository.existsById(token.toString())) {
            token = UUID.randomUUID();
        }
        return token;
    }

    private EmailMessage buildEmailMessage(String email, PasswordResetRequest passwordResetRequest) {
        return EmailMessage.builder()
                .recipient(email)
                .subject("Password reset")
                .template(EmailTemplate.ResetPassword)
                .param("resetUrl", buildUrl(passwordResetRequest))
                .build();
    }

    private String buildUrl(PasswordResetRequest passwordResetRequest) {
        return urlProcessor.processRelativeUrl(passwordResetProperties.getUrl(),
                                               param("token", passwordResetRequest.getToken()));
    }

    @Override
    public Client findClientByPasswordResetRequestToken(String token) {
        return passwordResetRequestRepository.findById(token)
                .filter(this::isRequestValid)
                .stream().peek(passwordResetRequestRepository::delete)
                .findFirst()
                .orElseThrow(() -> new InvalidPasswordResetRequest("This token isn't identified any valid request."))
                .getClient();
    }

    private boolean isRequestValid(PasswordResetRequest passwordResetRequest) {
        return passwordResetRequest.getExpirationDate().isAfter(LocalDateTime.now());
    }
}
