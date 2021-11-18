package com.example.final_project.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class NotPendingOrderCancellingException extends ResponseStatusException {

    public NotPendingOrderCancellingException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}
