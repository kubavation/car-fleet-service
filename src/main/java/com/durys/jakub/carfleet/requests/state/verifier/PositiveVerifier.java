package com.durys.jakub.carfleet.requests.state.verifier;

import com.durys.jakub.carfleet.requests.WithState;
import com.durys.jakub.carfleet.requests.state.ChangeCommand;
import com.durys.jakub.carfleet.requests.state.State;

import java.util.function.BiFunction;

public class PositiveVerifier<T extends WithState> implements BiFunction<State<T>, ChangeCommand, Boolean> {

    @Override
    public Boolean apply(State<T> state, ChangeCommand command) {
        return true;
    }
}
