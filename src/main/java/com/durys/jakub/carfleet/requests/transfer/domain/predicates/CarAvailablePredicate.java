package com.durys.jakub.carfleet.requests.transfer.domain.predicates;

import com.durys.jakub.carfleet.cars.availability.CarAvailabilityService;
import com.durys.jakub.carfleet.requests.drivertransfer.domain.DriverTransferRequest;
import com.durys.jakub.carfleet.requests.drivertransfer.domain.commands.ChangeTransportInformationCommand;
import com.durys.jakub.carfleet.requests.transfer.domain.TransferRequest;
import com.durys.jakub.carfleet.requests.transfer.domain.commands.ChangeCarCommand;
import com.durys.jakub.carfleet.state.ChangeCommand;
import com.durys.jakub.carfleet.state.State;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.function.BiFunction;

@RequiredArgsConstructor
public class CarAvailablePredicate implements BiFunction<State<TransferRequest>, ChangeCommand, Boolean> {

    private final CarAvailabilityService carAvailabilityService;

    @Override
    public Boolean apply(State<TransferRequest> driverTransferRequestState, ChangeCommand changeCommand) {

        ChangeCarCommand command = (ChangeCarCommand) changeCommand;

        return carAvailabilityService.available(command.getCarId(), command.getTransferFrom(), command.getTransferTo());
    }

}
