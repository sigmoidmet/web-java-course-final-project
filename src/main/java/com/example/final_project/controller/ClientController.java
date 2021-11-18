package com.example.final_project.controller;

import com.example.final_project.infrastructure.OnCreate;
import com.example.final_project.model.Client;
import com.example.final_project.pojo.PasswordResetRequestDto;
import com.example.final_project.service.ClientService;
import com.example.final_project.service.PasswordResetRequestService;
import lombok.RequiredArgsConstructor;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@Validated
public class ClientController {

    private final ClientService clientService;

    private final PasswordResetRequestService passwordResetRequestService;

    @Validated(OnCreate.class)
    @PostMapping("/register")
    public Client registerClient(@RequestBody @Valid Client client) {
        return clientService.register(client);
    }

    @PostMapping("clients/password/reset/request")
    public void requestPasswordReset(@RequestParam String email) {
        passwordResetRequestService.createNewRequestAndNotifyClient(email);
    }

    @PatchMapping("clients/password")
    public void resetPassword(@RequestBody PasswordResetRequestDto passwordResetRequest) {
        Client client = passwordResetRequestService.findClientByPasswordResetRequestToken(passwordResetRequest.getToken());
        clientService.resetPassword(client.getId(), passwordResetRequest.getNewPassword());
    }

}
