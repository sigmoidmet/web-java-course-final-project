package com.example.final_project.infrastructure;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FieldValidation {
    private final String field;

    private final String message;

    private final String rejectedValue;
}
