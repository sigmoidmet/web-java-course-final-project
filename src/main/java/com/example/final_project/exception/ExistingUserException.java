package com.example.final_project.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;


public class ExistingUserException extends ResponseStatusException {

    public ExistingUserException(String reason) {
        super(HttpStatus.CONFLICT, reason);
    }
}
