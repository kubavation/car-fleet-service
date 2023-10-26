package com.durys.jakub.carfleet.common.errors;

public class ValidationError extends RuntimeException {

    public ValidationError(String message) {
        super(message);
    }

}
