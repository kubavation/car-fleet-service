package com.durys.jakub.carfleet.requests.state;

import com.durys.jakub.carfleet.requests.Request;
import com.durys.jakub.carfleet.requests.state.predicates.NegativePredicate;
import com.durys.jakub.carfleet.requests.state.verifier.PositiveVerifier;
import com.durys.jakub.carfleet.requests.vo.RequestContent;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Predicate;

public class State {

    private final String name;

    private Request request;


    private Predicate<State> contentChangePredicate = new NegativePredicate();

    private State afterContentChangeState;

    private final Map<State, List<BiFunction<State, ChangeCommand, Boolean>>> stateChangePredicates = new HashMap<>();

    private final List<BiFunction<Request, ChangeCommand, Void>> afterStateChangeActions = new ArrayList<>();

    public State(String name) {
        this.name = name;
        addStateChangePredicates(this, List.of(new PositiveVerifier()));
    }

    public void init(Request request) {
        this.request = request;
        this.request.setState(name);
    }


    public State changeContent(RequestContent content){
        if (!isContentEditable())
            return this;

        State newState = afterContentChangeState;//local variable just to focus attention
        if (newState.contentChangePredicate.test(this)){
            newState.init(request);
            this.request.changeCurrentContent(content);
            return newState;
        }

        return this;
    }


    public State changeState(ChangeCommand command){
        State desiredState = find(command.getDesiredState());
        if (desiredState == null)
            return this;

        List<BiFunction<State, ChangeCommand, Boolean>> predicates = stateChangePredicates
                .getOrDefault(desiredState, Collections.emptyList());

        if (predicates.stream().allMatch(e -> e.apply(this, command))) {
            desiredState.init(request);
            desiredState.afterStateChangeActions.forEach(e -> e.apply(request, command));
            return desiredState;
        }

        return this;
    }


    public Request request(){
        return request;
    }

    public Map<State, List<BiFunction<State, ChangeCommand, Boolean>>> getStateChangePredicates() {
        return stateChangePredicates;
    }

    public Predicate<State> getContentChangePredicate() {
        return contentChangePredicate;
    }

    public boolean isContentEditable(){
        return afterContentChangeState != null;
    }

    void addStateChangePredicates(State toState, List<BiFunction<State, ChangeCommand, Boolean>> predicatesToAdd) {
        if (stateChangePredicates.containsKey(toState)) {
            List<BiFunction<State, ChangeCommand, Boolean>> predicates = stateChangePredicates.get(toState);
            predicates.addAll(predicatesToAdd);
        }
        else {
            stateChangePredicates.put(toState, predicatesToAdd);
        }
    }

    void addAfterStateChangeAction(BiFunction<Request, ChangeCommand, Void> action) {
        afterStateChangeActions.add(action);
    }

    void setAfterContentChangeState(State toState) {
        afterContentChangeState = toState;
    }

    void setContentChangePredicate(Predicate<State> predicate) {
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
}
