package com.durys.jakub.carfleet.requests.drivertransfer.domain.predicates;


import com.durys.jakub.carfleet.common.errors.ValidationError;
import com.durys.jakub.carfleet.requests.drivertransfer.domain.DriverTransferRequest;
import com.durys.jakub.carfleet.requests.drivertransfer.domain.commands.ChangeTransportInformationCommand;
import com.durys.jakub.carfleet.state.ChangeCommand;
import com.durys.jakub.carfleet.state.PredicateResult;
import com.durys.jakub.carfleet.state.State;
import jdk.dynalink.Operation;

import java.util.function.BiFunction;

public class DriverNotEmptyVerifier implements BiFunction<State<DriverTransferRequest>, ChangeCommand, PredicateResult> {

    @Override
    public PredicateResult apply(State<DriverTransferRequest> driverTransferRequestState, ChangeCommand changeDriverCommand) {

        var command = (ChangeTransportInformationCommand) changeDriverCommand;

        if (command.getDriverId() == null) {
            return PredicateResult.failure(new ValidationError("Driver have to be assigned"));
        }

        return PredicateResult.success();
    }
}
