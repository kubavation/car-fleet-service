package com.durys.jakub.carfleet.requests.state;

import com.durys.jakub.carfleet.requests.Flowable;

import java.util.*;
import java.util.function.BiFunction;

public class StateTransition<T extends Flowable<T>> {

    private State<T> from;
    private State<T> to;

    private final Set<BiFunction<T, ChangeCommand, Void>> afterStateChangeActions = new HashSet<>();
    private final Set<BiFunction<State<T>, ChangeCommand, Boolean>> stateChangePredicates = new HashSet<>();
//
//    private final Map<com.durys.jakub.carfleet.requests.state.State<T>, List<BiFunction<com.durys.jakub.carfleet.requests.state.State<T>, ChangeCommand, Boolean>>> stateChangePredicates = new HashMap<>();
//    private final Set<BiFunction<T, ChangeCommand, Void>> afterStateChangeActions = new HashSet<>();


    public StateTransition(State<T> from, State<T> to) {
        this.from = from;
        this.to = to;
    }

    public void addAction(BiFunction<T, ChangeCommand, Void> action) {
        afterStateChangeActions.add(action);
    }

    public void addPredicate(BiFunction<State<T>, ChangeCommand, Boolean> predicate) {
        stateChangePredicates.add(predicate);
    }


    public void setTo(State<T> to) {
        this.to = to;
    }

    public State<T> getFrom() {
        return from;
    }

    public State<T> getTo() {
        return to;
    }

     public Set<BiFunction<T, ChangeCommand, Void>> getAfterStateChangeActions() {
        return afterStateChangeActions;
    }

    public Set<BiFunction<State<T>, ChangeCommand, Boolean>> getStateChangePredicates() {
        return stateChangePredicates;
    }
}
