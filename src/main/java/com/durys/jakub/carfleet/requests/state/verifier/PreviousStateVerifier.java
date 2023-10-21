package com.durys.jakub.carfleet.requests.state.verifier;

import com.durys.jakub.carfleet.requests.state.ChangeCommand;
import com.durys.jakub.carfleet.requests.state.State;

import java.util.function.BiFunction;

public class PreviousStateVerifier implements BiFunction<State, ChangeCommand, Boolean> {
    private final String stateDescriptor;

    public PreviousStateVerifier(String stateDescriptor) {
        this.stateDescriptor = stateDescriptor;
    }

    @Override
    public Boolean apply(State state, ChangeCommand command) {
        return state.getName().equals(stateDescriptor);
    }
}
