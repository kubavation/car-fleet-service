package com.durys.jakub.carfleet.requests.transfer.domain.state.actions;

import com.durys.jakub.carfleet.cars.domain.Car;
import com.durys.jakub.carfleet.cars.domain.CarsRepository;
import com.durys.jakub.carfleet.requests.transfer.domain.TransferRequest;
import com.durys.jakub.carfleet.requests.transfer.domain.state.commands.AssignTransferCarCommand;
import com.durys.jakub.carfleet.state.ChangeCommand;

import java.util.function.BiFunction;

public class AssignTransferCar implements BiFunction<TransferRequest, ChangeCommand, Void> {

    private final CarsRepository carsRepository;

    public AssignTransferCar(CarsRepository carsRepository) {
        this.carsRepository = carsRepository;
    }

    @Override
    public Void apply(TransferRequest transferRequest, ChangeCommand changeCommand) {
        var command = (AssignTransferCarCommand) changeCommand;
        Car car = carsRepository.load(command.getCarId());
        transferRequest.assign(car);
        return null;
    }
}

