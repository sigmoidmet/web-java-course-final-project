package com.example.final_project.infrastructure;

import lombok.Getter;

public enum EmailTemplate {

    ResetPassword("reset-password-message");

    @Getter
    private final String templateName;

    EmailTemplate(String templateName) {
        this.templateName = templateName;
    }
}
