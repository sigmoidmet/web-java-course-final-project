package com.example.final_project.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class InvalidPasswordResetRequest extends ResponseStatusException {

    public InvalidPasswordResetRequest(String reason) {
        super(HttpStatus.BAD_REQUEST, reason);
    }
}
