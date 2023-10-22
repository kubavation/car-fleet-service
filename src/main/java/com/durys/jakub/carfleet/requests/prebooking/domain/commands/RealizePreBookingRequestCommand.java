package com.durys.jakub.carfleet.requests.prebooking.domain.commands;

import com.durys.jakub.carfleet.requests.RequestId;
import com.durys.jakub.carfleet.requests.prebooking.domain.PreBookingTransferRequestStatus;
import com.durys.jakub.carfleet.requests.state.ChangeCommand;

public class RealizePreBookingRequestCommand extends ChangeCommand {

    private final RequestId requestId;

    public RealizePreBookingRequestCommand(RequestId requestId) {
        super(PreBookingTransferRequestStatus.ARCHIVED);
        this.requestId = requestId;
    }

    public RequestId getRequestId() {
        return requestId;
    }
}
