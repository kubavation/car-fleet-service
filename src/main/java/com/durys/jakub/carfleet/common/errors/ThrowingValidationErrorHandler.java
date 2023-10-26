package com.durys.jakub.carfleet.common.errors;

public class ThrowingValidationErrorHandler implements ValidationErrorHandler {

    @Override
    public void handle(ValidationError validationError) {
        throw validationError;
    }
}
