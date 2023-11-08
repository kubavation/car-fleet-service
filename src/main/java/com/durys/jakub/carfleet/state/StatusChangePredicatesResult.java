package com.durys.jakub.carfleet.state;

import com.durys.jakub.carfleet.common.errors.ValidationError;
import com.durys.jakub.carfleet.common.errors.ValidationErrors;

import java.util.Collections;
import java.util.List;

class StatusChangePredicatesResult {

    enum Status {
        Success,
        Failure
    }

    private final Status status;
    private final ValidationErrors errors;

    StatusChangePredicatesResult(Status status, List<ValidationError> errors) {
        this.status = status;
        this.errors = new ValidationErrors(errors);
    }

    public static StatusChangePredicatesResult from(List<PredicateResult> results) {

        List<PredicateResult> failedPredicates = results.stream()
                .filter(PredicateResult::failed)
                .toList();

        if (failedPredicates.isEmpty()) {
            return new StatusChangePredicatesResult(Status.Success, Collections.emptyList());
        }

        return new StatusChangePredicatesResult(Status.Failure, errorsFrom(failedPredicates));
    }

    private static List<ValidationError> errorsFrom(List<PredicateResult> predicates) {
        return predicates
                .stream()
                .flatMap(predicate -> predicate.errors().stream())
                .toList();
    }

    public boolean succeeded() {
        return status == Status.Success;
    }

    public boolean failed() {
        return !succeeded();
    }

    public ValidationErrors errors() {
        return errors;
    }


}
