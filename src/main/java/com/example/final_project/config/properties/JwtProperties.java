package com.example.final_project.config.properties;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConfigurationProperties(prefix = "jwt")
@Getter
@ConstructorBinding
@RequiredArgsConstructor
public class JwtProperties {

    private final String secretKey;

    private final long validityInMs;
}
