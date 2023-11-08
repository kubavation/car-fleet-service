package com.durys.jakub.carfleet.state;

import com.durys.jakub.carfleet.common.errors.ValidationError;
import com.durys.jakub.carfleet.common.errors.ValidationErrors;
import io.vavr.control.Either;
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


    public Either<ValidationErrors, State<T>> changeState(ChangeCommand command) {

        log.info("changing state to {}", command.getDesiredState());

        StateTransition<T> transition = findStatusChangedTransition(command.getDesiredState());

        if (transition == null) {
            throw new RuntimeException("Invalid transition");
        }

        StatusChangePredicatesResult predicatesResult =
                checkStatusChangePredicates(command, transition.getStateChangePredicates());

        if (predicatesResult.succeeded()) {
            transition.to().init(object);
            transition.getAfterStateChangeActions().forEach(e -> e.apply(object, command));
            return Either.right(transition.to());
        }

        return Either.left(predicatesResult.errors());
    }


    public Either<ValidationErrors, State<T>> changeContent(T content) {

        StateTransition<T> contentChangedTransition = findContentChangedTransition(content.state());

        if (contentChangedTransition == null) {
            throw new RuntimeException("Content change not possible");
        }

        //todo validation (predicates)
        State<T> state = contentChangedTransition.to();
        state.init(object);
        object.setContent(content);

        return Either.right(state);
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
                .filter(transition -> transition.to().name.equals(toName))
                .flatMap(transition -> transition.getAfterStateChangeActions().stream())
                .collect(Collectors.toSet());
    }

    private StateTransition<T> findStatusChangedTransition(String desiredState) {
        return getPossibleTransitions()
                .stream()
                .filter(transition -> transition.to().name.equals(desiredState))
                .filter(StateTransition::statusChangedTransition)
                .findFirst()
                .orElse(null);
    }


    private StateTransition<T> findContentChangedTransition(String currentState) {
        return getPossibleTransitions()
                .stream()
                .filter(transition -> transition.from().name.equals(currentState))
                .filter(StateTransition::contentChangedTransition)
                .findFirst()
                .orElse(null);
    }

    private StatusChangePredicatesResult checkStatusChangePredicates(ChangeCommand command,
                            Set<BiFunction<State<T>, ChangeCommand, PredicateResult>> predicates) {

        List<PredicateResult> result = predicates
                .stream()
                .map(predicate -> predicate.apply(this, command))
                .toList();

        return StatusChangePredicatesResult.from(result);
    }

    @Override
    public String toString() {
        return name;
    }
}
