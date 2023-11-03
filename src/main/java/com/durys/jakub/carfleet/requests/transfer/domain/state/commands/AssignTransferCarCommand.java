package com.durys.jakub.carfleet.requests.transfer.domain.state.commands;

import com.durys.jakub.carfleet.cars.domain.CarId;
import com.durys.jakub.carfleet.requests.drivertransfer.domain.DriverTransferRequestStatus;
import com.durys.jakub.carfleet.state.ChangeCommand;

public class AssignTransferCarCommand extends ChangeCommand {

    private final CarId carId;

    public AssignTransferCarCommand(CarId carId) {
        super(DriverTransferRequestStatus.ACCEPTED);
        this.carId = carId;
    }

    public CarId getCarId() {
        return carId;
    }
}
