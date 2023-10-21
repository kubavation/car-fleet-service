package com.durys.jakub.carfleet.requests.drivertransfer.predicates;


import com.durys.jakub.carfleet.requests.drivertransfer.DriverTransferRequest;
import com.durys.jakub.carfleet.requests.drivertransfer.commands.ChangeDriverCommand;
import com.durys.jakub.carfleet.requests.state.ChangeCommand;
import com.durys.jakub.carfleet.requests.state.State;

import java.util.function.BiFunction;

public class DriverNotEmptyVerifier implements BiFunction<State<DriverTransferRequest>, ChangeCommand, Boolean> {

    @Override
    public Boolean apply(State<DriverTransferRequest> driverTransferRequestState, ChangeCommand changeDriverCommand) {
        return ((ChangeDriverCommand) changeDriverCommand).getDriverId() != null;
    }
}
