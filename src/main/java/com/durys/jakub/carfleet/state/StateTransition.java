package com.durys.jakub.carfleet.state;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiFunction;

import static com.durys.jakub.carfleet.state.StateTransition.Mode.ContentChanges;
import static com.durys.jakub.carfleet.state.StateTransition.Mode.StatusChanges;

class StateTransition<T extends Flowable<T>> {

    enum Mode {
        StatusChanges,
        ContentChanges
    }

    private final State<T> from;
    private final State<T> to;
    private final Mode mode;

    private final Set<BiFunction<T, ChangeCommand, Void>> afterStateChangeActions = new HashSet<>();
    private final Set<BiFunction<State<T>, ChangeCommand, PredicateResult>> stateChangePredicates = new HashSet<>();

    private StateTransition(State<T> from, State<T> to, Mode mode) {
        this.from = from;
        this.to = to;
        this.mode = mode;
    }

    static <T extends Flowable<T>> StateTransition<T> onStatusChange(State<T> from, State<T> to) {
        return new StateTransition<>(from, to, StatusChanges);
    }

    static <T extends Flowable<T>> StateTransition<T> onContentChange(State<T> from, State<T> to) {
        return new StateTransition<>(from, to, ContentChanges);
    }

    void addAction(BiFunction<T, ChangeCommand, Void> action) {
        afterStateChangeActions.add(action);
    }

    void addPredicate(BiFunction<State<T>, ChangeCommand, PredicateResult> predicate) {
        stateChangePredicates.add(predicate);
    }
    
    State<T> from() {
        return from;
    }

    State<T> to() {
        return to;
    }

    boolean statusChangedTransition() {
        return mode == StatusChanges;
    }

    boolean contentChangedTransition() {
        return mode == ContentChanges;
    }

    Set<BiFunction<T, ChangeCommand, Void>> getAfterStateChangeActions() {
        return afterStateChangeActions;
    }

    Set<BiFunction<State<T>, ChangeCommand, PredicateResult>> getStateChangePredicates() {
        return stateChangePredicates;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StateTransition<?> that = (StateTransition<?>) o;
        return Objects.equals(from, that.from) && Objects.equals(to, that.to) && mode == that.mode;
    }

    @Override
    public int hashCode() {
        return Objects.hash(from, to, mode);
    }
}
