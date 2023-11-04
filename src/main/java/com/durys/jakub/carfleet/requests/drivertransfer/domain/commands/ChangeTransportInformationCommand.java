package com.durys.jakub.carfleet.requests.drivertransfer.domain.commands;

import com.durys.jakub.carfleet.cars.domain.CarId;
import com.durys.jakub.carfleet.drivers.domain.DriverId;
import com.durys.jakub.carfleet.requests.drivertransfer.domain.DriverTransferRequest;
import com.durys.jakub.carfleet.state.ChangeCommand;

public class ChangeTransportInformationCommand extends ChangeCommand {

    private final DriverId driverId;
    private final CarId carId;

    public ChangeTransportInformationCommand(DriverId driverId, CarId carId) {
        super(DriverTransferRequest.Status.ACCEPTED);
        this.driverId = driverId;
        this.carId = carId;
    }


    public DriverId getDriverId() {
        return driverId;
    }

    public CarId getCarId() {
        return carId;
    }
}
