package com.durys.jakub.carfleet.requests.transfer.domain.state.actions;

import com.durys.jakub.carfleet.requests.transfer.domain.TransferRequest;
import com.durys.jakub.carfleet.requests.transfer.domain.state.commands.ChangeCarCommand;
import com.durys.jakub.carfleet.state.ChangeCommand;

import java.util.function.BiFunction;

public class ChangeCarInformation implements BiFunction<TransferRequest, ChangeCommand, Void> {

    @Override
    public Void apply(TransferRequest transferRequest, ChangeCommand changeCommand) {
        var command = (ChangeCarCommand) changeCommand;
        transferRequest.assignCar(command.getCarId());
        return null;
    }
}

