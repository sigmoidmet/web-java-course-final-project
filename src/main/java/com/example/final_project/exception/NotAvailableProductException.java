package com.example.final_project.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class NotAvailableProductException extends ResponseStatusException {

    public NotAvailableProductException(Long desiredQuantity, long productId) {
        super(HttpStatus.BAD_REQUEST, String.format("There are no %d products by id %d in the store",
                                                    desiredQuantity, productId));
    }
}
