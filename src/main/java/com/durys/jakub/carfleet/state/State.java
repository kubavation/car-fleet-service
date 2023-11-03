package com.durys.jakub.carfleet.state;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

@Slf4j
public class State<T extends Flowable<T>> {

    private T object;
    private final String name;
    private final List<StateTransition<T>> possibleTransitions;

    public State(String name) {
        this.name = name;
        this.possibleTransitions = new ArrayList<>();
    }

    public void init(T object) {
        this.object = object;
        this.object.setState(name);
    }


    public State<T> changeState(ChangeCommand command) {

        log.info("changing state to {}", command.getDesiredState());

        StateTransition<T> transition = findStatusChangedTransition(command.getDesiredState());


        if (transition == null) {
            throw new RuntimeException("Invalid transition");
        }

        Set<BiFunction<State<T>, ChangeCommand, Boolean>> predicates = transition.getStateChangePredicates();

        if (predicates.stream().allMatch(e -> e.apply(this, command))) {
            transition.getTo().init(object);
            transition.getAfterStateChangeActions().forEach(e -> e.apply(object, command));
            return transition.getTo();
        }

        return this;
    }


    public State<T> changeContent(T content) {

        StateTransition<T> contentChangedTransition = findContentChangedTransition(content.state());

        if (contentChangedTransition == null) {
            throw new RuntimeException("Content change not possible");
        }

        //todo validation (predicates)
        State<T> state = contentChangedTransition.getTo();
        state.init(object);
        this.object.setContent(content);
        return state;
    }

    public String name() {
        return name;
    }

    public T getObject() {
        return object;
    }

    public void addTransition(StateTransition<T> transition) {
        possibleTransitions.add(transition);
    }

    public List<StateTransition<T>> getPossibleTransitions() {
        return possibleTransitions;
    }

    public Set<BiFunction<T, ChangeCommand, Void>> afterStateChangeActions(String toName) {
        return possibleTransitions.stream()
                .filter(transition -> transition.getTo().name.equals(toName))
                .flatMap(transition -> transition.getAfterStateChangeActions().stream())
                .collect(Collectors.toSet());
    }

    private StateTransition<T> findStatusChangedTransition(String desiredState) {
        return getPossibleTransitions()
                .stream()
                .filter(transition -> transition.getTo().name.equals(desiredState))
                .filter(StateTransition::statusChangedTransition)
                .findFirst()
                .orElse(null);
    }


    private StateTransition<T> findContentChangedTransition(String currentState) {
        return getPossibleTransitions()
                .stream()
                .filter(transition -> transition.getFrom().name.equals(currentState))
                .filter(StateTransition::contentChangedTransition)
                .findFirst()
                .orElse(null);
    }

    @Override
    public String toString() {
        return name;
    }
}
