package com.durys.jakub.carfleet.requests.transfer.domain.state.commands;

import com.durys.jakub.carfleet.cars.domain.CarId;
import com.durys.jakub.carfleet.requests.drivertransfer.domain.DriverTransferRequestStatus;
import com.durys.jakub.carfleet.state.ChangeCommand;

import java.time.LocalDateTime;

public class AssignTransferCarCommand extends ChangeCommand {

    private final CarId carId;
    private final LocalDateTime transferFrom;
    private final LocalDateTime transferTo;

    public AssignTransferCarCommand(CarId carId, LocalDateTime transferFrom, LocalDateTime transferTo) {
        super(DriverTransferRequestStatus.ACCEPTED);
        this.carId = carId;
        this.transferFrom = transferFrom;
        this.transferTo = transferTo;
    }

    public CarId getCarId() {
        return carId;
    }

    public LocalDateTime getTransferFrom() {
        return transferFrom;
    }

    public LocalDateTime getTransferTo() {
        return transferTo;
    }
}
