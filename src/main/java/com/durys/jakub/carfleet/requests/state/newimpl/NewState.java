package com.durys.jakub.carfleet.requests.state.newimpl;

import com.durys.jakub.carfleet.requests.state.ChangeCommand;
import com.durys.jakub.carfleet.requests.state.State;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

@Slf4j
public class NewState<T> {

    private final String name;
    private final Set<StateTransition<T>> possibleTransitions;

    NewState(String name) {
        this.name = name;
        this.possibleTransitions = new HashSet<>();
    }

    public NewState<T> changeState(ChangeCommand command){

        log.info("chaning state to {}", command.getDesiredState());

        NewState<T> desiredState = find(command.getDesiredState());

        System.out.println(desiredState.getName());

        if (desiredState == null)
            return this;

        List<BiFunction<State<T>, ChangeCommand, Boolean>> predicates = stateChangePredicates
                .getOrDefault(desiredState, Collections.emptyList());

        predicates.stream()
                .forEach(c -> System.out.println(c));

        if (predicates.stream().allMatch(e -> e.apply(this, command))) {
            desiredState.init(object);
            desiredState.afterStateChangeActions.forEach(c -> System.out.println(c));
            desiredState.afterStateChangeActions.forEach(e -> e.apply(object, command));
            return desiredState;
        }

        return this;
    }

    public String name() {
        return name;
    }

    void addTransition(StateTransition<T> transition) {
        possibleTransitions.add(transition);
    }

    public Set<StateTransition<T>> getPossibleTransitions() {
        return possibleTransitions;
    }

    public Set<BiFunction<T, ChangeCommand, Void>> afterStateChangeActions(String toName) {
        return possibleTransitions.stream()
                .filter(transition -> transition.getTo().name.equals(toName))
                .flatMap(transition -> transition.getAfterStateChangeActions().stream())
                .collect(Collectors.toSet());
    }

    private NewState<T> find(String desiredState) {
        return stateChangePredicates.keySet()
                .stream()
                .filter(e -> e.name.equals(desiredState))
                .findFirst()
                .orElse(null);
    }

}
