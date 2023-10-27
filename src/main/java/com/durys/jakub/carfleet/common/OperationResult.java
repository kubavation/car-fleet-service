package com.durys.jakub.carfleet.common;

import com.durys.jakub.carfleet.common.errors.ValidationError;

import java.util.Set;

public class OperationResult {

    public enum Status {
        Success,
        Failure
    }

    private final Set<ValidationError> validationErrors;
    private final Status status;


    public static OperationResult success() {
        return new OperationResult(Status.Success, Set.of());
    }

    public static OperationResult failure(Set<ValidationError> validationErrors) {
        return new OperationResult(Status.Failure, validationErrors);
    }

    private OperationResult(Status status, Set<ValidationError> validationErrors) {
        this.status = status;
        this.validationErrors = validationErrors;
    }

    public Status status() {
        return status;
    }

    public Set<ValidationError> validationErrors() {
        return validationErrors;
    }
}
