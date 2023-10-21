package com.durys.jakub.carfleet.requests.state.predicates;


import com.durys.jakub.carfleet.requests.Request;
import com.durys.jakub.carfleet.requests.state.ChangeCommand;
import com.durys.jakub.carfleet.requests.state.State;

import java.util.function.BiFunction;

public class DriverNotEmptyVerifier implements BiFunction<State<Request>, ChangeCommand, Boolean> {

    @Override
    public Boolean apply(State<Request> state, ChangeCommand command) {
        return command.getParams() != null; //todo check if driver is not null
    }
}
