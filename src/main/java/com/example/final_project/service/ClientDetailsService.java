package com.example.final_project.service;

import com.example.final_project.repository.ClientRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientDetailsService implements UserDetailsService {

    private final ClientRepository clientRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return clientRepository.findByEmail(username)
                .map(client -> User
                        .withUsername(client.getEmail())
                        .password(client.getPassword())
                        .disabled(client.isDisabled())
                        .authorities("CLIENT")
                        .build())
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }
}
