package com.durys.jakub.carfleet.requests.state.newimpl;

import com.durys.jakub.carfleet.requests.Flowable;
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
public class NewState<T extends Flowable<T>> {

    private T object;
    private final String name;
    private final Set<StateTransition<T>> possibleTransitions;

    NewState(String name) {
        this.name = name;
        this.possibleTransitions = new HashSet<>();
    }

    public void init(T object) {
        this.object = object;
        this.object.setState(name);
    }


    public NewState<T> changeState(ChangeCommand command){

        log.info("chaning state to {}", command.getDesiredState());

        StateTransition<T> transition = findTransition(command.getDesiredState());

        if (transition == null) {
            throw new RuntimeException("Invalid transition");
        }

        Set<BiFunction<NewState<T>, ChangeCommand, Boolean>> predicates = transition.getStateChangePredicates();

        if (predicates.stream().allMatch(e -> e.apply(this, command))) {
            transition.getTo().init(object);
            transition.getAfterStateChangeActions().forEach(e -> e.apply(object, command));
            return transition.getTo();
        }

        return this;
    }


    public NewState<T> changeContent(T content){
//        if (!isContentEditable())
//            return this;
//
//        State<T> newState = afterContentChangeState;//local variable just to focus attention
//        if (newState.contentChangePredicate.test(this)){
//            newState.init(object);
//            this.object.setContent(content);
//            return newState;
//        }

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

    private StateTransition<T> findTransition(String desiredState) {
        return getPossibleTransitions()
                .stream()
                .filter(transition -> transition.getFrom().name.equals(desiredState))
                .findFirst()
                .orElse(null);
    }

}
