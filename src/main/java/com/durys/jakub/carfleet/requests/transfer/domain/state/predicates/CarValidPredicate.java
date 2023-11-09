package com.durys.jakub.carfleet.requests.transfer.domain.state.predicates;

import com.durys.jakub.carfleet.cars.availability.CarAvailabilityService;
import com.durys.jakub.carfleet.cars.domain.Car;
import com.durys.jakub.carfleet.cars.domain.CarId;
import com.durys.jakub.carfleet.cars.domain.CarsRepository;
import com.durys.jakub.carfleet.common.errors.ValidationError;
import com.durys.jakub.carfleet.requests.transfer.domain.TransferRequest;
import com.durys.jakub.carfleet.requests.transfer.domain.state.commands.AssignTransferCarCommand;
import com.durys.jakub.carfleet.state.ChangeCommand;
import com.durys.jakub.carfleet.state.PredicateResult;
import com.durys.jakub.carfleet.state.State;
import lombok.RequiredArgsConstructor;

import java.util.function.BiFunction;

@RequiredArgsConstructor
public class CarValidPredicate implements BiFunction<State<TransferRequest>, ChangeCommand, PredicateResult> {

    private final CarAvailabilityService carAvailabilityService;
    private final CarsRepository carsRepository;

    @Override
    public PredicateResult apply(State<TransferRequest> driverTransferRequestState, ChangeCommand changeCommand) {

        AssignTransferCarCommand command = (AssignTransferCarCommand) changeCommand;

        CarId carId = command.getCarId();

        if (carId == null) {
            return PredicateResult.failure(new ValidationError("Car not specified"));
        }

        Car car = carsRepository.load(carId);
        TransferRequest request = driverTransferRequestState.getObject();


        if (!car.getCarType().equals(request.carType())) {
            return PredicateResult.failure(new ValidationError("Invalid assigned car type"));
        }

        if (!carAvailabilityService.available(command.getCarId(), request.transferFrom(), request.transferTo())) {
            return PredicateResult.failure(new ValidationError("Car not available"));
        }

        return PredicateResult.success();
    }

}
