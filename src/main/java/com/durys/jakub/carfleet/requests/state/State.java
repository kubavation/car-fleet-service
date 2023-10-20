package com.durys.jakub.carfleet.requests.state;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Predicate;

public class State {

    private Object requestContent;


    private Predicate<State> contentChangePredicate = null; //todo

    private State afterContentChangeState;

    private final Map<State, List<BiFunction<State, ChangeCommand, Boolean>>> stateChangePredicates = new HashMap<>();

    private final List<BiFunction<DocumentHeader, ChangeCommand, Void>> afterStateChangeActions = new ArrayList<>();
}
