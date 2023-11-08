package com.durys.jakub.carfleet.requests.transfer.domain.state.predicates;

import com.durys.jakub.carfleet.common.errors.ValidationError;
import com.durys.jakub.carfleet.requests.transfer.domain.TransferRequest;
import com.durys.jakub.carfleet.state.ChangeCommand;
import com.durys.jakub.carfleet.state.PredicateResult;
import com.durys.jakub.carfleet.state.State;
import lombok.RequiredArgsConstructor;

import java.util.function.BiFunction;

@RequiredArgsConstructor
public class CarAssignedPredicate implements BiFunction<State<TransferRequest>, ChangeCommand, PredicateResult> {

    @Override
    public PredicateResult apply(State<TransferRequest> state, ChangeCommand changeCommand) {

        TransferRequest request = state.getObject();

        if (request.assignedCar() == null) {
            return PredicateResult.failure(new ValidationError("Car not assigned"));
        }

        return PredicateResult.success();
    }

}
