package com.durys.jakub.carfleet.common.errors;

import java.util.List;

public class ValidationErrors {

    private final List<ValidationError> errors;

    public ValidationErrors(List<ValidationError> errors) {
        this.errors = errors;
    }

    public List<ValidationError> errors() {
        return errors;
    }
}
