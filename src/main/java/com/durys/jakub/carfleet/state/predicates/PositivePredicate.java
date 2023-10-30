package com.durys.jakub.carfleet.state.predicates;

import com.durys.jakub.carfleet.state.Flowable;
import com.durys.jakub.carfleet.state.State;

import java.util.function.Predicate;

public class PositivePredicate<T extends Flowable<T>> implements Predicate<State<T>> {

    @Override
    public boolean test(State state) {
        return true;
    }
}

