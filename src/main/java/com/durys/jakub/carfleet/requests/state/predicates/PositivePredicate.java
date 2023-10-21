package com.durys.jakub.carfleet.requests.state.predicates;

import com.durys.jakub.carfleet.requests.Flowable;
import com.durys.jakub.carfleet.requests.state.State;

import java.util.function.Predicate;

public class PositivePredicate<T extends Flowable<T>> implements Predicate<State<T>> {

    @Override
    public boolean test(State state) {
        return true;
    }
}

