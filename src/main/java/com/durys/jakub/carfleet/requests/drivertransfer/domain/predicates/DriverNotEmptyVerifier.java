package com.durys.jakub.carfleet.requests.drivertransfer.domain.predicates;


import com.durys.jakub.carfleet.requests.drivertransfer.domain.DriverTransferRequest;
import com.durys.jakub.carfleet.requests.drivertransfer.domain.commands.ChangeTransportInformationCommand;
import com.durys.jakub.carfleet.requests.state.ChangeCommand;
import com.durys.jakub.carfleet.requests.state.State;

import java.util.function.BiFunction;

public class DriverNotEmptyVerifier implements BiFunction<State<DriverTransferRequest>, ChangeCommand, Boolean> {

    @Override
    public Boolean apply(State<DriverTransferRequest> driverTransferRequestState, ChangeCommand changeDriverCommand) {
        return ((ChangeTransportInformationCommand) changeDriverCommand).getDriverId() != null;
    }
}
