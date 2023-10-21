package com.durys.jakub.carfleet.requests.drivertransfer.commands;

import com.durys.jakub.carfleet.drivers.DriverId;
import com.durys.jakub.carfleet.requests.drivertransfer.DriverTransferRequestAssembler;
import com.durys.jakub.carfleet.requests.state.ChangeCommand;

public class ChangeDriverCommand extends ChangeCommand {

    private final DriverId driverId;

    public ChangeDriverCommand(DriverId driverId) {
        super(DriverTransferRequestAssembler.Status.ACCEPTED);
        this.driverId = driverId;
    }


    public DriverId getDriverId() {
        return driverId;
    }
}
