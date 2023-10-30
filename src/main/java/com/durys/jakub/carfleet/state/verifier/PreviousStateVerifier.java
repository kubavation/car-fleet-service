package com.durys.jakub.carfleet.state.verifier;

import com.durys.jakub.carfleet.state.Flowable;
import com.durys.jakub.carfleet.state.ChangeCommand;
import com.durys.jakub.carfleet.state.State;

import java.util.function.BiFunction;

public class PreviousStateVerifier<T extends Flowable<T>> implements BiFunction<State<T>, ChangeCommand, Boolean> {
    private final String stateDescriptor;

    public PreviousStateVerifier(String stateDescriptor) {
        this.stateDescriptor = stateDescriptor;
    }

    @Override
    public Boolean apply(State<T> state, ChangeCommand command) {
        return state.name().equals(stateDescriptor);
    }
}
