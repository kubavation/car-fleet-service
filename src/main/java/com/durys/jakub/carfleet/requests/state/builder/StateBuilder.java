package com.durys.jakub.carfleet.requests.state.builder;

import com.durys.jakub.carfleet.requests.Flowable;
import com.durys.jakub.carfleet.requests.state.*;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

public class StateBuilder<T extends Flowable<T>> {

    public static class TransitionBuilder<T extends Flowable<T>>
            implements StateTransitionDestinationBuilder<T>,
                       StateTransitionActionBuilder<T> {

        private final StateBuilder<T> builder;
        private StateTransition<T> transition;

        public TransitionBuilder(StateBuilder<T> builder) {
            this.builder = builder;
        }

        @Override
        public StateTransitionActionBuilder<T> to(Enum<?> to) {
            State<T> state = builder.getOrPut(to.name());
            transition = new StateTransition<>(builder.currentState, state);
            return this;
        }

        @Override
        public StateTransitionActionBuilder<T> execute(BiFunction<T, ChangeCommand, Void> action) {
            transition.addAction(action);
            return this;
        }

        @Override
        public StateTransitionActionBuilder<T> check(BiFunction<State<T>, ChangeCommand, Boolean> checkingFunction) {
            transition.addPredicate(checkingFunction);
            return this; //todo
        }

        @Override
        public StateConfig<T> build() {
            addTransition();
            return builder.build();
        }


        @Override
        public StateBuilder<T> and() {
            addTransition();
            return builder;
        }

        private void addTransition() {
            builder.currentState.addTransition(transition);
        }

    }

    public interface StateTransitionDestinationBuilder<T extends Flowable<T>> extends DefaultBuilder<T> {
        StateTransitionActionBuilder<T> to(Enum<?> to);
    }

    public interface StateTransitionActionBuilder<T extends Flowable<T>> extends DefaultBuilder<T> {
        StateTransitionActionBuilder<T> execute(BiFunction<T, ChangeCommand, Void> action);
        StateTransitionActionBuilder<T> check(BiFunction<State<T>, ChangeCommand, Boolean> predicate);
        StateConfig<T> build();
    }

    public interface DefaultBuilder<T extends Flowable<T>> {
        StateBuilder<T> and();
    }

    private final Map<String, State<T>> configuredStates = new HashMap<>();

    private State<T> currentState;
    private State<T> initialState;

    public StateBuilder() {}

    private StateConfig<T> build() {
        return new DefaultStateConfig<>(this);
    }

    public StateTransitionDestinationBuilder<T> beginWith(Enum<?> state) {
        if (initialState != null)
            throw new IllegalStateException("Initial state already set to: " + initialState.name());

        initialState = getOrPut(state.name());
        return from(state);
    }

    public StateTransitionDestinationBuilder<T> from(Enum<?> from) {
        this.currentState = getOrPut(from.name());
        return new TransitionBuilder<>(this);
    }


    private State<T> getOrPut(String stateName) {
        return configuredStates.computeIfAbsent(stateName, State::new);
    }

    private State<T> get(String state) {
        return configuredStates.get(state);
    }

    public Map<String, State<T>> getConfiguredStates() {
        return configuredStates;
    }

    public State<T> getInitialState() {
        return initialState;
    }
}
