package com.durys.jakub.carfleet.requests.state;

import com.durys.jakub.carfleet.requests.Flowable;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

public class StateBuilder<T extends Flowable<T>> implements StateConfig<T> {


    public static class TransitionBuilder<T extends Flowable<T>> {

        private final StateBuilder<T> builder;
        private StateTransition<T> transition;

        public TransitionBuilder(StateBuilder<T> builder) {
            this.builder = builder;
        }

        public TransitionBuilder<T> to(Enum<?> to) {

            State<T> state = builder.getOrPut(to.name());

            transition = new StateTransition<>(builder.currentState, state);
            return this;
        }

        public TransitionBuilder<T> execute(BiFunction<T, ChangeCommand, Void> action) {
            transition.addAction(action);
            builder.currentState.addTransition(transition);
            return this;
        }

        public StateBuilder<T> and() {
            builder.currentState.addTransition(transition);
            return builder;
        }
    }

    private final Map<String, State<T>> configuredStates = new HashMap<>();

    private State<T> currentState;
    private State<T> initialState;

    public StateBuilder() {
    }

    @Override
    public State<T> begin(T request) {
        request.setState(initialState.name());
        return recreate(request);
    }

    @Override
    public State<T> recreate(T request) {
        State<T> state = configuredStates.get(request.state());
        state.init(request);
        return state;
    }


    public TransitionBuilder<T> beginWith(Enum<?> state) {
        if (initialState != null)
            throw new IllegalStateException("Initial state already set to: " + initialState.name());

        initialState = getOrPut(state.name());
        return from(state);
    }

    public TransitionBuilder<T> from(Enum<?> from) {
        this.currentState = getOrPut(from.name());
        return new TransitionBuilder<>(this);
    }


    private State<T> getOrPut(String stateName) {
        if (!configuredStates.containsKey(stateName)) {
            configuredStates.put(stateName, new State<>(stateName));
        }
        return configuredStates.get(stateName);
    }

    public Map<String, State<T>> getConfiguredStates() {
        return configuredStates;
    }
}
