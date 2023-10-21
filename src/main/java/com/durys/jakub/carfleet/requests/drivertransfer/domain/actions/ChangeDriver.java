package com.durys.jakub.carfleet.requests.drivertransfer.domain.actions;

import com.durys.jakub.carfleet.requests.drivertransfer.domain.DriverTransferRequest;
import com.durys.jakub.carfleet.requests.drivertransfer.domain.commands.ChangeDriverCommand;
import com.durys.jakub.carfleet.requests.state.ChangeCommand;

import java.util.function.BiFunction;

public class ChangeDriver implements BiFunction<DriverTransferRequest, ChangeCommand, Void> {

    @Override
    public Void apply(DriverTransferRequest driverTransferRequest, ChangeCommand changeCommand) {

        var command = (ChangeDriverCommand) changeCommand;

        driverTransferRequest.setDriverId(command.getDriverId());
        return null;
    }
}

