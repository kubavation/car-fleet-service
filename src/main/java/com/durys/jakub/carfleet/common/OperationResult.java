package com.durys.jakub.carfleet.common;

import java.util.Set;

public class OperationResult {

    public enum Status {
        Success,
        Failure
    }

    private final Set<String> errorMessages;
    private final Status status;


    public static OperationResult success() {
        return new OperationResult(Status.Success, Set.of());
    }

    public static OperationResult failure( Set<String> errorMessages) {
        return new OperationResult(Status.Failure, errorMessages);
    }

    private OperationResult(Status status, Set<String> errorMessages) {
        this.status = status;
        this.errorMessages = errorMessages;
    }

    public Status status() {
        return status;
    }

    public Set<String> errorMessages() {
        return errorMessages;
    }
}
