package com.durys.jakub.carfleet.state;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

class StatusChangePredicatesResult {

    enum Status {
        Success,
        Failure
    }

    private final Status status;
    private final List<Exception> errors;

    StatusChangePredicatesResult(Status status, List<Exception> errors) {
        this.status = status;
        this.errors = errors;
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

    private static List<Exception> errorsFrom(List<PredicateResult> predicates) {
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

    public List<Exception> errors() {
        return errors;
    }


}
