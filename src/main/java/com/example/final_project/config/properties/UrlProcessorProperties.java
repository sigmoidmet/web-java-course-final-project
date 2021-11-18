package com.example.final_project.config.properties;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConfigurationProperties(prefix = "url-processor")
@Getter
@ConstructorBinding
@RequiredArgsConstructor
public class UrlProcessorProperties {

    private final String scheme;

    private final String host;

    private final int port;
}
