package com.durys.jakub.carfleet.requests.drivertransfer.domain.predicates;

import com.durys.jakub.carfleet.cars.availability.CarAvailabilityService;
import com.durys.jakub.carfleet.requests.drivertransfer.domain.DriverTransferRequest;
import com.durys.jakub.carfleet.requests.drivertransfer.domain.commands.ChangeTransportInformationCommand;
import com.durys.jakub.carfleet.state.ChangeCommand;
import com.durys.jakub.carfleet.state.PredicateResult;
import com.durys.jakub.carfleet.state.State;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.function.BiFunction;

@RequiredArgsConstructor
public class CarAvailablePredicate implements BiFunction<State<DriverTransferRequest>, ChangeCommand, PredicateResult> {

    private final CarAvailabilityService carAvailabilityService;

    @Override
    public PredicateResult apply(State<DriverTransferRequest> driverTransferRequestState, ChangeCommand changeDriverCommand) {

        return PredicateResult.from(
                carAvailabilityService.available(((ChangeTransportInformationCommand) changeDriverCommand).getCarId(),
                        LocalDateTime.now(), LocalDateTime.now())); //todo
    }

}
