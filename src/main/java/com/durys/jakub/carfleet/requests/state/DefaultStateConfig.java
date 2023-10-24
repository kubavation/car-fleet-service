package com.durys.jakub.carfleet.requests.state;

import com.durys.jakub.carfleet.requests.Flowable;

import java.util.Collections;
import java.util.List;

public class DefaultStateConfig<T extends Flowable<T>> implements StateConfig<T> {

    private final StateBuilder<T> stateBuilder;

    public DefaultStateConfig(StateBuilder<T> stateBuilder) {
        this.stateBuilder = stateBuilder;
        validateStateConfiguration();
    }

    @Override
    public State<T> begin(T object) {
        object.setState(stateBuilder.getInitialState().name());
        return recreate(object);
    }

    @Override
    public State<T> recreate(T object) {
        State<T> state = stateBuilder.getConfiguredStates().get(object.state());
        state.init(object);
        return state;
    }

    private void validateStateConfiguration() {
        if (stateBuilder == null) {
            throw new RuntimeException("Invalid state configuration. Provided empty state builder");
        }

        List<StateTransition<T>> duplicateTransitions = stateBuilder.getConfiguredStates()
                .values()
                .stream()
                .flatMap(state -> state.getPossibleTransitions()
                        .stream()
                        .filter(ts -> Collections.frequency(state.getPossibleTransitions(), ts) > 1))
                .toList();

        if (!duplicateTransitions.isEmpty()) {
            throw new RuntimeException("Duplicate transition");
        }

    }
}
