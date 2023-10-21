package com.durys.jakub.carfleet.requests.state.predicates;

import com.durys.jakub.carfleet.requests.state.State;

import java.util.function.Predicate;

public class NegativePredicate implements Predicate<State> {

    @Override
    public boolean test(State state) {
        return false;
    }
}

