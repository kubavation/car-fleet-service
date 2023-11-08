package com.durys.jakub.carfleet.requests.transfer.domain.state.actions;

import com.durys.jakub.carfleet.requests.transfer.domain.TransferRequest;
import com.durys.jakub.carfleet.requests.transfer.domain.event.TransferRequestAccepted;
import com.durys.jakub.carfleet.state.ChangeCommand;

import java.util.function.BiFunction;

public class AcceptTransferRequest implements BiFunction<TransferRequest, ChangeCommand, Void> {

    @Override
    public Void apply(TransferRequest transferRequest, ChangeCommand changeCommand) {
        transferRequest.append(new TransferRequestAccepted(transferRequest));
        return null;
    }
}

