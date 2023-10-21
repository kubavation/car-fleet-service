package com.durys.jakub.carfleet.requests.state.verifier;

import com.durys.jakub.carfleet.requests.Flowable;
import com.durys.jakub.carfleet.requests.state.ChangeCommand;
import com.durys.jakub.carfleet.requests.state.State;

import java.util.function.BiFunction;

public class PositiveVerifier<T extends Flowable<T>> implements BiFunction<State<T>, ChangeCommand, Boolean> {

    @Override
    public Boolean apply(State<T> state, ChangeCommand command) {
        return true;
    }
}
