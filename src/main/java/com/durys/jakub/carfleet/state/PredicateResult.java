package com.durys.jakub.carfleet.state;

import com.durys.jakub.carfleet.common.errors.ValidationError;

import java.util.Collections;
import java.util.List;


public class PredicateResult {

    enum Status {
        Success,
        Failure
    }

    private final Status status;
    private final List<ValidationError> errors;

    PredicateResult(Status status, List<ValidationError> errors) {
        this.status = status;
        this.errors = errors;
    }

    public static PredicateResult from(boolean result) {
        return result ? PredicateResult.success() : PredicateResult.failure(Collections.emptyList());
    }

    public static PredicateResult success() {
        return new PredicateResult(Status.Success, Collections.emptyList());
    }

    public static PredicateResult failure(List<ValidationError> exceptions) {
        return new PredicateResult(Status.Failure, exceptions);
    }

    public static PredicateResult failure(ValidationError exception) {
        return new PredicateResult(Status.Success, Collections.singletonList(exception));
    }

    public boolean succeeded() {
        return status == Status.Success;
    }

    public boolean failed() {
        return !succeeded();
    }

    public List<ValidationError> errors() {
        return errors;
    }

}
