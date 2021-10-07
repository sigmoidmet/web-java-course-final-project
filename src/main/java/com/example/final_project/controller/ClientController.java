package com.example.final_project.controller;

import com.example.final_project.dto.ClientCredentialsDto;
import com.example.final_project.infrastructure.OnCreate;
import com.example.final_project.model.Client;
import com.example.final_project.service.ClientService;
import lombok.RequiredArgsConstructor;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@Validated
public class ClientController {

    private final ClientService clientService;

    @Validated(OnCreate.class)
    @PostMapping("/register")
    public Client registerClient(@RequestBody @Valid Client client) {
        return clientService.register(client);
    }


}
