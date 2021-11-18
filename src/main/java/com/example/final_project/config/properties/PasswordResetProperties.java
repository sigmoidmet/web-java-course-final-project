package com.example.final_project.config.properties;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

import java.time.Duration;

@ConfigurationProperties(prefix = "password-reset-settings")
@Getter
@ConstructorBinding
@RequiredArgsConstructor
public class PasswordResetProperties {

    private final Duration tokenExpirationDuration;

    private final String url;
}
