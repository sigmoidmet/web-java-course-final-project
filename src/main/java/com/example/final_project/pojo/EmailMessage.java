package com.example.final_project.pojo;

import com.example.final_project.infrastructure.EmailTemplate;
import lombok.Builder;
import lombok.Data;
import lombok.Singular;

import java.util.Map;

@Data
@Builder
public class EmailMessage {

    private final String recipient;

    private final String subject;

    private final EmailTemplate template;

    @Singular
    private final Map<String, String> params;
}
