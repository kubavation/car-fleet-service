package com.durys.jakub.carfleet.requests.drivertransfer.actions;

import com.durys.jakub.carfleet.requests.drivertransfer.DriverTransferRequest;
import com.durys.jakub.carfleet.requests.drivertransfer.commands.ChangeDriverCommand;
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

