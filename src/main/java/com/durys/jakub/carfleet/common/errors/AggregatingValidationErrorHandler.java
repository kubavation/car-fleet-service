package com.durys.jakub.carfleet.common.errors;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class AggregatingValidationErrorHandler implements ValidationErrorHandler {

    private final Set<ValidationError> errors = new HashSet<>();

    @Override
    public void handle(ValidationError validationError) {
        errors.add(validationError);
    }

    public boolean hasErrors() {
        return !errors.isEmpty();
    }

    public Set<String> errorMessages() {
        return errors.stream()
                .map(ValidationError::getMessage)
                .collect(Collectors.toSet());
    }
}
