package com.example.final_project.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class OrderCancellingByAnotherClientException extends ResponseStatusException {

    public OrderCancellingByAnotherClientException(String message) {
        super(HttpStatus.FORBIDDEN, message);
    }
}
