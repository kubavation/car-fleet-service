package com.durys.jakub.carfleet.requests.state;

import com.durys.jakub.carfleet.requests.Flowable;
import com.durys.jakub.carfleet.requests.state.predicates.PositivePredicate;
import com.durys.jakub.carfleet.requests.state.verifier.PreviousStateVerifier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

public class StateBuilder<T extends Flowable<T>> implements StateConfig<T> {

    public static class FinalStateConfig<T extends Flowable<T>> {
        private final State<T> state;
        //new
        private final StateBuilder<T> builder;

        FinalStateConfig(State<T> state, StateBuilder<T> builder) {
            this.state = state;
            this.builder = builder;
        }

        public FinalStateConfig<T> action(BiFunction<T, ChangeCommand, Void> action) {
            state.addAfterStateChangeAction(action);
            return this;
        }

        public StateBuilder<T> and() {
            return builder;
        }

        public StateBuilder<T> build() {
            return builder;
        }

    }

    private enum Mode {
        CONTENT_CHANGE,
        STATE_CHANGE
    }


    private Mode mode;
    private final Map<String, State<T>> states = new HashMap<>();

    private State<T> fromState;
    private State<T> initialState;
    private List<BiFunction<State<T>, ChangeCommand, Boolean>> predicates;

    @Override
    public State<T> begin(T request) {
        request.setState(initialState.getName());
        return recreate(request);
    }

    @Override
    public State<T> recreate(T request) {
        State<T> state = states.get(request.state());
        state.init(request);
        return state;
    }


    public StateBuilder<T> beginWith(String stateName) {
        if (initialState != null)
            throw new IllegalStateException("Initial state already set to: " + initialState.getName());

        StateBuilder<T> config = from(stateName);
        initialState = fromState;
        return config;
    }

    public StateBuilder<T> from(String stateName) {
        mode = Mode.STATE_CHANGE;
        predicates = new ArrayList<>();
        fromState = getOrPut(stateName);
        return this;
    }


    public StateBuilder<T> check(BiFunction<State<T>, ChangeCommand, Boolean> checkingFunction) {
        mode = Mode.STATE_CHANGE;
        predicates.add(checkingFunction);
        return this;
    }

    public FinalStateConfig<T> to(String stateName) {
        State<T> toState = getOrPut(stateName);

        switch (mode){
            case STATE_CHANGE:
                predicates.add(new PreviousStateVerifier<>(fromState.getName()));
                fromState.addStateChangePredicates(toState, predicates);
                break;
            case CONTENT_CHANGE:
                fromState.setAfterContentChangeState(toState);
                toState.setContentChangePredicate(new PositivePredicate<>());
        }

        predicates = null;
        fromState = null;
        mode = null;

        return new FinalStateConfig<>(toState, this);
    }

    /**
     * Adds a rule of state change after a content change
     */
    public StateBuilder<T> whenContentChanged() {
        mode = Mode.CONTENT_CHANGE;
        return this;
    }

    private State<T> getOrPut(String stateName) {
        if (!states.containsKey(stateName))
            states.put(stateName, new State<>(stateName));
        return states.get(stateName);
    }

    public StateBuilder<T> and() {
        return this;
    }
}
