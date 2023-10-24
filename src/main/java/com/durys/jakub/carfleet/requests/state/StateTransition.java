package com.durys.jakub.carfleet.requests.state;

import com.durys.jakub.carfleet.requests.Flowable;

import java.util.*;
import java.util.function.BiFunction;

import static com.durys.jakub.carfleet.requests.state.StateTransition.Mode.StatusChanges;

public class StateTransition<T extends Flowable<T>> {

    public enum Mode {
        StatusChanges,
        ContentChanges
    }

    private final State<T> from;
    private final State<T> to;
    private final Mode mode;

    private final Set<BiFunction<T, ChangeCommand, Void>> afterStateChangeActions = new HashSet<>();
    private final Set<BiFunction<State<T>, ChangeCommand, Boolean>> stateChangePredicates = new HashSet<>();


    public StateTransition(State<T> from, State<T> to) {
        this(from, to, StatusChanges);
    }

    public StateTransition(State<T> from, State<T> to, Mode mode) {
        this.from = from;
        this.to = to;
        this.mode = mode;
    }

    public void addAction(BiFunction<T, ChangeCommand, Void> action) {
        afterStateChangeActions.add(action);
    }

    public void addPredicate(BiFunction<State<T>, ChangeCommand, Boolean> predicate) {
        stateChangePredicates.add(predicate);
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
