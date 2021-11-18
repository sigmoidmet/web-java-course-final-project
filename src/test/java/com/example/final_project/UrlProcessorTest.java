package com.example.final_project;

import com.example.final_project.config.properties.UrlProcessorProperties;
import com.example.final_project.service.UrlProcessorImpl;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UrlProcessorTest {

    private final UrlProcessorProperties urlProcessorProperties = new UrlProcessorProperties("http",
                                                                                             "localhost",
                                                                                             8081);

    private final UrlProcessorImpl urlProcessor = new UrlProcessorImpl(urlProcessorProperties);

    @Test
    void processRelativeUrl() {
        assertEquals("http://localhost:8081/1", urlProcessor.processRelativeUrl("1"));
    }
}
