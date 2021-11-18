package com.example.final_project.exception;

public class DisabledClientTriedToLoginException extends RuntimeException {

    public DisabledClientTriedToLoginException(String message) {
        super(message);
    }
}
