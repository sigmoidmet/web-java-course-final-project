package com.example.final_project.service;

import com.example.final_project.config.properties.UrlProcessorProperties;
import com.example.final_project.pojo.QueryParam;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@RequiredArgsConstructor
public class UrlProcessorImpl implements UrlProcessor {

    private final UrlProcessorProperties urlProcessorProperties;

    @Override
    public String processRelativeUrl(String relativeUrl, QueryParam... queryParams) {
        UriComponentsBuilder builder = UriComponentsBuilder.newInstance()
                .scheme(urlProcessorProperties.getScheme())
                .host(urlProcessorProperties.getHost())
                .port(urlProcessorProperties.getPort())
                .path(relativeUrl);

        for (var param : queryParams) {
            builder = builder.queryParam(param.getName(), param.getValue());
        }

        return builder.toUriString();
    }
}
