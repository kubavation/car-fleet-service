package com.durys.jakub.carfleet.state.predicates;

import java.util.function.Predicate;

public class NegativePredicate<T> implements Predicate<T> {

    @Override
    public boolean test(T state) {
        return false;
    }
}

