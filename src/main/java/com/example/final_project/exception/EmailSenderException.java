package com.example.final_project.exception;

public class EmailSenderException extends RuntimeException {
    public EmailSenderException(String message, Exception cause) {
        super(message, cause);
    }
}
