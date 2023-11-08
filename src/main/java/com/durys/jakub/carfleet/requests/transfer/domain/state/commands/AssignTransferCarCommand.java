package com.durys.jakub.carfleet.requests.transfer.domain.state.commands;

import com.durys.jakub.carfleet.cars.domain.CarId;
import com.durys.jakub.carfleet.requests.drivertransfer.domain.DriverTransferRequest;
import com.durys.jakub.carfleet.state.ChangeCommand;

import java.util.Objects;

public class AssignTransferCarCommand extends ChangeCommand {

    private final CarId carId;

    public AssignTransferCarCommand(CarId carId) {
        super(DriverTransferRequest.Status.EDITED);
        this.carId = carId;
    }

    public CarId getCarId() {
        return carId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        AssignTransferCarCommand that = (AssignTransferCarCommand) o;
        return Objects.equals(carId, that.carId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), carId);
    }
}
