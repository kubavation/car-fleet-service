package com.durys.jakub.carfleet.requests.state.verifier;

import com.durys.jakub.carfleet.requests.state.ChangeCommand;
import com.durys.jakub.carfleet.requests.state.State;

import java.util.function.BiFunction;

public class PositiveVerifier implements BiFunction<State, ChangeCommand, Boolean> {

    @Override
    public Boolean apply(State state, ChangeCommand command) {
        return true;
    }
}
