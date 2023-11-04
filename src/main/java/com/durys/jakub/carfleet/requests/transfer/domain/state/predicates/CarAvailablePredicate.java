package com.durys.jakub.carfleet.requests.transfer.domain.state.predicates;

import com.durys.jakub.carfleet.cars.availability.CarAvailabilityService;
import com.durys.jakub.carfleet.requests.transfer.domain.TransferRequest;
import com.durys.jakub.carfleet.requests.transfer.domain.state.commands.AssignTransferCarCommand;
import com.durys.jakub.carfleet.state.ChangeCommand;
import com.durys.jakub.carfleet.state.PredicateResult;
import com.durys.jakub.carfleet.state.State;
import lombok.RequiredArgsConstructor;

import java.util.function.BiFunction;

@RequiredArgsConstructor
public class CarAvailablePredicate implements BiFunction<State<TransferRequest>, ChangeCommand, PredicateResult> {

    private final CarAvailabilityService carAvailabilityService;

    @Override
    public PredicateResult apply(State<TransferRequest> driverTransferRequestState, ChangeCommand changeCommand) {

        AssignTransferCarCommand command = (AssignTransferCarCommand) changeCommand;

        TransferRequest request = driverTransferRequestState.getObject();

        return PredicateResult.from(
                carAvailabilityService.available(command.getCarId(), request.transferFrom(), request.transferTo()));
    }

}
