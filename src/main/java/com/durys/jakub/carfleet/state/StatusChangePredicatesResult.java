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
    private final Set<Exception> errors;

    StatusChangePredicatesResult(Status status, Set<Exception> errors) {
        this.status = status;
        this.errors = errors;
    }

    public static StatusChangePredicatesResult from(List<PredicateResult> results) {

        Set<PredicateResult> failedPredicates = results.stream()
                .filter(PredicateResult::failed)
                .collect(Collectors.toSet());

        if (failedPredicates.isEmpty()) {
            return new StatusChangePredicatesResult(Status.Success, Collections.emptySet());
        }

        return new StatusChangePredicatesResult(Status.Failure, errorsFrom(failedPredicates));
    }

    private static Set<Exception> errorsFrom(Set<PredicateResult> predicates) {
        return predicates
                .stream()
                .flatMap(predicate -> predicate.errors().stream())
                .collect(Collectors.toSet());
    }

    public boolean succeeded() {
        return status == Status.Success;
    }

    public boolean failed() {
        return !succeeded();
    }

    public Set<Exception> errors() {
        return errors;
    }


}
