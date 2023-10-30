package com.durys.jakub.carfleet.requests.drivertransfer.domain.actions;

import com.durys.jakub.carfleet.requests.drivertransfer.domain.DriverTransferRequest;
import com.durys.jakub.carfleet.requests.drivertransfer.domain.commands.ChangeTransportInformationCommand;
import com.durys.jakub.carfleet.state.ChangeCommand;

import java.util.function.BiFunction;

public class ChangeTransportInformation implements BiFunction<DriverTransferRequest, ChangeCommand, Void> {

    @Override
    public Void apply(DriverTransferRequest driverTransferRequest, ChangeCommand changeCommand) {

        var command = (ChangeTransportInformationCommand) changeCommand;

        driverTransferRequest.setDriverId(command.getDriverId());
        driverTransferRequest.setCarId(command.getCarId());
        return null;
    }
}

