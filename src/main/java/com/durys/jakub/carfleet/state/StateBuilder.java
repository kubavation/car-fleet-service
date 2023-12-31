package com.durys.jakub.carfleet.state;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

public class StateBuilder<T extends Flowable<T>> implements InitialStateBuilder<T> {

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
            transition = StateTransition.onStatusChange(builder.currentState, state);
            return this;
        }

        @Override
        public StateTransitionActionBuilder<T> whenContentChangesTo(Enum<?> to) {
            State<T> state = builder.getOrPut(to.name());
            transition = StateTransition.onContentChange(builder.currentState, state);
            return this;
        }

        @Override
        public StateTransitionActionBuilder<T> execute(BiFunction<T, ChangeCommand, Void> action) {
            transition.addAction(action);
            return this;
        }

        @Override
        public StateTransitionActionBuilder<T> check(BiFunction<State<T>, ChangeCommand, PredicateResult> checkingFunction) {
            transition.addPredicate(checkingFunction);
            return this;
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


    private final Map<String, State<T>> configuredStates = new HashMap<>();

    private State<T> currentState;
    private State<T> initialState;

    StateBuilder() {}

    public static <T extends Flowable<T>> InitialStateBuilder<T> builderForClass(Class<T> clazz)  {
        return new StateBuilder<>();
    }

    private StateConfig<T> build() {
        return new DefaultStateConfig<>(this);
    }

    @Override
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

    public Map<String, State<T>> getConfiguredStates() {
        return configuredStates;
    }

    public State<T> getInitialState() {
        return initialState;
    }


    public interface StateTransitionDestinationBuilder<T extends Flowable<T>> extends DefaultBuilder<T> {
        StateTransitionActionBuilder<T> to(Enum<?> to);
        StateTransitionActionBuilder<T> whenContentChangesTo(Enum<?> to);
    }

    public interface StateTransitionActionBuilder<T extends Flowable<T>> extends DefaultBuilder<T> {
        StateTransitionActionBuilder<T> execute(BiFunction<T, ChangeCommand, Void> action);
        StateTransitionActionBuilder<T> check(BiFunction<State<T>, ChangeCommand, PredicateResult> predicate);
        StateConfig<T> build();
    }

    public interface DefaultBuilder<T extends Flowable<T>> {
        StateBuilder<T> and();
    }

}

