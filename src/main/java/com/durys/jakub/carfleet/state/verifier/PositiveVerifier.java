package com.durys.jakub.carfleet.state.verifier;

import com.durys.jakub.carfleet.state.Flowable;
import com.durys.jakub.carfleet.state.ChangeCommand;
import com.durys.jakub.carfleet.state.State;

import java.util.function.BiFunction;

public class PositiveVerifier<T extends Flowable<T>> implements BiFunction<State<T>, ChangeCommand, Boolean> {

    @Override
    public Boolean apply(State<T> state, ChangeCommand command) {
        return true;
    }
}
