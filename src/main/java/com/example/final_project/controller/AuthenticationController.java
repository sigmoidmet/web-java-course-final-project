package com.example.final_project.controller;

import com.example.final_project.authentication.JwtTokenProvider;
import com.example.final_project.dto.ClientCredentialsDto;
import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;

    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/login")
    public String login(@RequestBody ClientCredentialsDto clientCredentials) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(clientCredentials.getEmail(),
                                                                                   clientCredentials.getPassword()));

        return jwtTokenProvider.createToken(clientCredentials.getEmail(), null);
    }
}
