package com.durys.jakub.carfleet.requests.transfer.domain.actions;

import com.durys.jakub.carfleet.requests.transfer.domain.TransferRequest;
import com.durys.jakub.carfleet.requests.transfer.domain.commands.ChangeCarCommand;
import com.durys.jakub.carfleet.state.ChangeCommand;

import java.util.function.BiFunction;

public class ChangeCarInformation implements BiFunction<TransferRequest, ChangeCommand, Void> {

    @Override
    public Void apply(TransferRequest transferRequest, ChangeCommand changeCommand) {
        var command = (ChangeCarCommand) changeCommand;
        transferRequest.setUpCar(command.getCarId());
        return null;
    }
}

