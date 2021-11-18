package com.example.final_project.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class OrderWithEmptyCartException extends ResponseStatusException {

    public OrderWithEmptyCartException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}
