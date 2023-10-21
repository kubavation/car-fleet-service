package com.durys.jakub.carfleet.requests.state;

import com.durys.jakub.carfleet.requests.Request;
import com.durys.jakub.carfleet.requests.state.predicates.PositivePredicate;
import com.durys.jakub.carfleet.requests.state.verifier.PreviousStateVerifier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

public class StateBuilder implements StateConfig {

    public static class FinalStateConfig{
        private final State state;
        //new
        private final StateBuilder builder;

        FinalStateConfig(State state, StateBuilder builder) {
            this.state = state;
            this.builder = builder;
        }

        public FinalStateConfig action(BiFunction<Request, ChangeCommand, Void> action) {
            state.addAfterStateChangeAction(action);
            return this;
        }

        public StateBuilder and() {
            return builder;
        }

        public StateBuilder build() {
            return builder;
        }

    }

    private enum Mode {
        CONTENT_CHANGE,
        STATE_CHANGE
    }


    private Mode mode;
    private Map<String, State> states = new HashMap<>();

    private State fromState;
    private State initialState;
    private List<BiFunction<State, ChangeCommand, Boolean>> predicates;

    @Override
    public State begin(Request request) {
        request.setState(initialState.getName());
        return recreate(request);
    }

    @Override
    public State recreate(Request request) {
        State state = states.get(request.getState());
        state.init(request);
        return state;
    }


    public StateBuilder beginWith(String stateName) {
        if (initialState != null)
            throw new IllegalStateException("Initial state already set to: " + initialState.getName());

        StateBuilder config = from(stateName);
        initialState = fromState;
        return config;
    }

    public StateBuilder from(String stateName) {
        mode = Mode.STATE_CHANGE;
        predicates = new ArrayList<>();
        fromState = getOrPut(stateName);
        return this;
    }


    public StateBuilder check(BiFunction<State, ChangeCommand, Boolean> checkingFunction) {
        mode = Mode.STATE_CHANGE;
        predicates.add(checkingFunction);
        return this;
    }

    public FinalStateConfig to(String stateName) {
        State toState = getOrPut(stateName);

        switch (mode){
            case STATE_CHANGE:
                predicates.add(new PreviousStateVerifier(fromState.getName()));
                fromState.addStateChangePredicates(toState, predicates);
                break;
            case CONTENT_CHANGE:
                fromState.setAfterContentChangeState(toState);
                toState.setContentChangePredicate(new PositivePredicate());
        }

        predicates = null;
        fromState = null;
        mode = null;

        return new FinalStateConfig(toState, this);
    }

    /**
     * Adds a rule of state change after a content change
     */
    public StateBuilder whenContentChanged() {
        mode = Mode.CONTENT_CHANGE;
        return this;
    }

    private State getOrPut(String stateName) {
        if (!states.containsKey(stateName))
            states.put(stateName, new State(stateName));
        return states.get(stateName);
    }

    public StateBuilder and() {
        return this;
    }
}
