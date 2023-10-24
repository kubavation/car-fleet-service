package com.durys.jakub.carfleet.requests.state;

import com.durys.jakub.carfleet.requests.Flowable;
import com.durys.jakub.carfleet.requests.state.builder.StateBuilder;

public class DefaultStateConfig<T extends Flowable<T>> implements StateConfig<T> {

    private final StateBuilder<T> stateBuilder;

    public DefaultStateConfig(StateBuilder<T> stateBuilder) {
        this.stateBuilder = stateBuilder;
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
}
