package com.durys.jakub.carfleet.requests.drivertransfer.commands;

import com.durys.jakub.carfleet.drivers.DriverId;
import com.durys.jakub.carfleet.requests.state.ChangeCommand;

public class ChangeDriverCommand extends ChangeCommand {

    private final DriverId driverId;

    public ChangeDriverCommand(String desiredState, DriverId driverId) {
        super(desiredState);
        this.driverId = driverId;
    }

    public ChangeDriverCommand(Enum<?> desiredState, DriverId driverId) {
        super(desiredState);
        this.driverId = driverId;
    }

    public DriverId getDriverId() {
        return driverId;
    }
}
