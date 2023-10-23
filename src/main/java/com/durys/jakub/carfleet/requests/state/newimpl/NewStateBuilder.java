package com.durys.jakub.carfleet.requests.state.newimpl;

import com.durys.jakub.carfleet.requests.Flowable;
import com.durys.jakub.carfleet.requests.state.ChangeCommand;
import com.durys.jakub.carfleet.requests.state.StateConfig;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

public class NewStateBuilder<T extends Flowable<T>> implements StateConfig<T> {


    public static class TransitionBuilder<T extends Flowable<T>> {

        private final NewStateBuilder<T> builder;
        private StateTransition<T> transition;

        public TransitionBuilder(NewStateBuilder<T> builder) {
            this.builder = builder;
        }

        public TransitionBuilder<T> to(Enum<?> to) {

            NewState<T> state = builder.getOrPut(to.name());

            transition = new StateTransition<>(builder.currentState, state);
            return this;
        }

        public TransitionBuilder<T> execute(BiFunction<T, ChangeCommand, Void> action) {
            transition.addAction(action);
            builder.currentState.addTransition(transition);
            return this;
        }

        public NewStateBuilder<T> and() {
            builder.currentState.addTransition(transition);
            return builder;
        }
    }

    private final Map<String, NewState<T>> configuredStates = new HashMap<>();

    private NewState<T> currentState;
    private NewState<T> initialState;

    public NewStateBuilder() {
    }

    @Override
    public NewState<T> begin(T request) {
        request.setState(initialState.name());
        return recreate(request);
    }

    @Override
    public NewState<T> recreate(T request) {
        NewState<T> state = configuredStates.get(request.state());
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


    private NewState<T> getOrPut(String stateName) {
        if (!configuredStates.containsKey(stateName)) {
            configuredStates.put(stateName, new NewState<>(stateName));
        }
        return configuredStates.get(stateName);
    }

    public Map<String, NewState<T>> getConfiguredStates() {
        return configuredStates;
    }
}
