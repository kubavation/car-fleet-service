package com.durys.jakub.carfleet.requests.state.newimpl;

import com.durys.jakub.carfleet.requests.Flowable;
import com.durys.jakub.carfleet.requests.state.ChangeCommand;
import com.durys.jakub.carfleet.requests.state.State;
import com.durys.jakub.carfleet.requests.state.predicates.NegativePredicate;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Predicate;

public class StateTransition<T extends Flowable<T>> {

    private NewState<T> from;
    private NewState<T> to;

    private final Set<BiFunction<T, ChangeCommand, Void>> afterStateChangeActions = new HashSet<>();
    private final Set<BiFunction<NewState<T>, ChangeCommand, Boolean>> stateChangePredicates = new HashSet<>();
//
//    private final Map<com.durys.jakub.carfleet.requests.state.State<T>, List<BiFunction<com.durys.jakub.carfleet.requests.state.State<T>, ChangeCommand, Boolean>>> stateChangePredicates = new HashMap<>();
//    private final Set<BiFunction<T, ChangeCommand, Void>> afterStateChangeActions = new HashSet<>();


    public StateTransition(NewState<T> from, NewState<T> to) {
        this.from = from;
        this.to = to;
    }

    public void addAction(BiFunction<T, ChangeCommand, Void> action) {
        afterStateChangeActions.add(action);
    }

    public void addPredicate(BiFunction<NewState<T>, ChangeCommand, Boolean> predicate) {
        stateChangePredicates.add(predicate);
    }


    public void setTo(NewState<T> to) {
        this.to = to;
    }

    public NewState<T> getFrom() {
        return from;
    }

    public NewState<T> getTo() {
        return to;
    }

     public Set<BiFunction<T, ChangeCommand, Void>> getAfterStateChangeActions() {
        return afterStateChangeActions;
    }

    public Set<BiFunction<NewState<T>, ChangeCommand, Boolean>> getStateChangePredicates() {
        return stateChangePredicates;
    }
}
