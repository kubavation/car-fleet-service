package com.durys.jakub.carfleet.requests.state;

import com.durys.jakub.carfleet.requests.Flowable;
import com.durys.jakub.carfleet.requests.state.predicates.NegativePredicate;
import com.durys.jakub.carfleet.requests.state.verifier.PositiveVerifier;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Predicate;

public class State<T extends Flowable<T>> {

    private final String name;

    private T object;


    private Predicate<State<T>> contentChangePredicate = new NegativePredicate<>();

    private State<T> afterContentChangeState;

    private final Map<State<T>, List<BiFunction<State<T>, ChangeCommand, Boolean>>> stateChangePredicates = new HashMap<>();

    private final List<BiFunction<T, ChangeCommand, Void>> afterStateChangeActions = new ArrayList<>();

    public State(String name) {
        this.name = name;
        addStateChangePredicates(this, List.of(new PositiveVerifier<T>()));
    }

    public void init(T request) {
        this.object = request;
        this.object.setState(name);
    }


    public State<T> changeContent(T content){
        if (!isContentEditable())
            return this;

        State<T> newState = afterContentChangeState;//local variable just to focus attention
        if (newState.contentChangePredicate.test(this)){
            newState.init(object);
            this.object.setContent(content);
            return newState;
        }

        return this;
    }


    public State<T> changeState(ChangeCommand command){
        State<T> desiredState = find(command.getDesiredState());
        if (desiredState == null)
            return this;

        List<BiFunction<State<T>, ChangeCommand, Boolean>> predicates = stateChangePredicates
                .getOrDefault(desiredState, Collections.emptyList());

        if (predicates.stream().allMatch(e -> e.apply(this, command))) {
            desiredState.init(object);
            desiredState.afterStateChangeActions.forEach(e -> e.apply(object, command));
            return desiredState;
        }

        return this;
    }


    public Map<State<T>, List<BiFunction<State<T>, ChangeCommand, Boolean>>> getStateChangePredicates() {
        return stateChangePredicates;
    }

    public Predicate<State<T>> getContentChangePredicate() {
        return contentChangePredicate;
    }

    public boolean isContentEditable(){
        return afterContentChangeState != null;
    }

    void addStateChangePredicates(State<T> toState, List<BiFunction<State<T>, ChangeCommand, Boolean>> predicatesToAdd) {
        if (stateChangePredicates.containsKey(toState)) {
            List<BiFunction<State<T>, ChangeCommand, Boolean>> predicates = stateChangePredicates.get(toState);
            predicates.addAll(predicatesToAdd);
        }
        else {
            stateChangePredicates.put(toState, predicatesToAdd);
        }
    }

    void addAfterStateChangeAction(BiFunction<T, ChangeCommand, Void> action) {
        afterStateChangeActions.add(action);
    }

    void setAfterContentChangeState(State<T> toState) {
        afterContentChangeState = toState;
    }

    void setContentChangePredicate(Predicate<State<T>> predicate) {
        contentChangePredicate = predicate;
    }

    private State<T> find(String desiredState) {
        return stateChangePredicates.keySet()
                .stream()
                .filter(e -> e.name.equals(desiredState))
                    .findFirst()
                    .orElse(null);
    }

    public String getName() {
        return name;
    }

    public T getObject() {
        return object;
    }

}
