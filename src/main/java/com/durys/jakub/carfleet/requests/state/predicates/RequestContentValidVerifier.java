package com.durys.jakub.carfleet.requests.state.predicates;


import com.durys.jakub.carfleet.requests.state.ChangeCommand;
import com.durys.jakub.carfleet.requests.state.State;

import java.util.function.BiFunction;

public class RequestContentValidVerifier implements BiFunction<State, ChangeCommand, Boolean> {

    @Override
    public Boolean apply(State state, ChangeCommand command) {
        return state.getRequest().getContent().getFrom() != null
                && state.getRequest().getContent().getTo() != null
                && state.getRequest().getContent().getPurpose() != null;
    }
}
