package com.durys.jakub.carfleet.requests.state.actions;

import com.durys.jakub.carfleet.drivers.DriverId;
import com.durys.jakub.carfleet.requests.Request;
import com.durys.jakub.carfleet.requests.state.ChangeCommand;
import com.durys.jakub.carfleet.requests.state.State;

import java.util.function.BiFunction;

public class ChangeDriver implements BiFunction<Request, ChangeCommand, Void> {

    @Override
    public Void apply(Request request, ChangeCommand changeCommand) {
        request.setDriverId(changeCommand.getParam("driverId", DriverId.class));
        return null;
    }
}

