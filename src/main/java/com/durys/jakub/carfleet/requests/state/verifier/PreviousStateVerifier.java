package com.durys.jakub.carfleet.requests.state.verifier;

import com.durys.jakub.carfleet.requests.WithState;
import com.durys.jakub.carfleet.requests.state.ChangeCommand;
import com.durys.jakub.carfleet.requests.state.State;

import java.util.function.BiFunction;

public class PreviousStateVerifier<T extends WithState> implements BiFunction<State<T>, ChangeCommand, Boolean> {
    private final String stateDescriptor;

    public PreviousStateVerifier(String stateDescriptor) {
        this.stateDescriptor = stateDescriptor;
    }

    @Override
    public Boolean apply(State<T> state, ChangeCommand command) {
        return state.getName().equals(stateDescriptor);
    }
}
