package com.durys.jakub.carfleet.requests.drivertransfer.actions;

import com.durys.jakub.carfleet.drivers.DriverId;
import com.durys.jakub.carfleet.requests.drivertransfer.DriverTransferRequest;
import com.durys.jakub.carfleet.requests.state.ChangeCommand;

import java.util.function.BiFunction;

public class ChangeDriver implements BiFunction<DriverTransferRequest, ChangeCommand, Void> {

    @Override
    public Void apply(DriverTransferRequest driverTransferRequest, ChangeCommand changeCommand) {
        driverTransferRequest.setDriverId(changeCommand.getParam("driverId", DriverId.class));
        return null;
    }
}

