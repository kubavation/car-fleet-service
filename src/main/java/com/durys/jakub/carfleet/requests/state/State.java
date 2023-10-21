package com.durys.jakub.carfleet.requests.state;

import com.durys.jakub.carfleet.requests.Request;
import com.durys.jakub.carfleet.requests.WithState;
import com.durys.jakub.carfleet.requests.state.predicates.NegativePredicate;
import com.durys.jakub.carfleet.requests.state.verifier.PositiveVerifier;
import com.durys.jakub.carfleet.requests.vo.RequestContent;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Predicate;

public class State<T extends WithState> {

    private final String name;

    private T request;


    private Predicate<State<T>> contentChangePredicate = new NegativePredicate<>();

    private State<T> afterContentChangeState;

    private final Map<State<T>, List<BiFunction<State<T>, ChangeCommand, Boolean>>> stateChangePredicates = new HashMap<>();

    private final List<BiFunction<T, ChangeCommand, Void>> afterStateChangeActions = new ArrayList<>();

    public State(String name) {
        this.name = name;
        addStateChangePredicates(this, List.of(new PositiveVerifier()));
    }

    public void init(T request) {
        this.request = request;
        this.request.setState(name);
    }


    public State<T> changeContent(RequestContent content){
        if (!isContentEditable())
            return this;

        State<T> newState = afterContentChangeState;//local variable just to focus attention
        if (newState.contentChangePredicate.test(this)){
            newState.init(request);
           // this.request.changeCurrentContent(content);
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
            desiredState.init(request);
            desiredState.afterStateChangeActions.forEach(e -> e.apply(request, command));
            return desiredState;
        }

        return this;
    }


    public T request(){
        return request;
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
            System.out.println(predicates);
            System.out.println(predicatesToAdd);
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

    private State find(String desiredState) {
        return stateChangePredicates.keySet()
                .stream()
                .filter(e -> e.name.equals(desiredState))
                    .findFirst()
                    .orElse(null);
    }

    public String getName() {
        return name;
    }

    public T getRequest() {
        return request;
    }

}
