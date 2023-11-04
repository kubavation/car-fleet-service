package com.durys.jakub.carfleet.requests.prebooking.domain.commands;

import com.durys.jakub.carfleet.sharedkernel.requests.RequesterId;
import com.durys.jakub.carfleet.requests.prebooking.domain.PreBookingTransferRequestStatus;
import com.durys.jakub.carfleet.state.ChangeCommand;

public class RealizePreBookingRequestCommand extends ChangeCommand {

    private final RequesterId requesterId; //todo

    public RealizePreBookingRequestCommand(RequesterId requesterId) {
        super(PreBookingTransferRequestStatus.ARCHIVED);
        this.requesterId = requesterId;
    }

    public RequesterId getRequesterId() {
        return requesterId;
    }
}
