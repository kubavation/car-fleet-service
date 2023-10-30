package com.durys.jakub.carfleet.requests.prebooking.domain.action;

import com.durys.jakub.carfleet.events.Events;
import com.durys.jakub.carfleet.requests.prebooking.domain.PreBookingTransferRequest;
import com.durys.jakub.carfleet.requests.prebooking.domain.commands.RealizePreBookingRequestCommand;
import com.durys.jakub.carfleet.requests.prebooking.domain.events.PreBookingRealized;
import com.durys.jakub.carfleet.state.ChangeCommand;
import lombok.RequiredArgsConstructor;

import java.util.function.BiFunction;

@RequiredArgsConstructor
public class RealizePreBookingRequest implements BiFunction<PreBookingTransferRequest, ChangeCommand, Void> {

    private final Events events;

    @Override
    public Void apply(PreBookingTransferRequest driverTransferRequest, ChangeCommand changeCommand) {

        var command = (RealizePreBookingRequestCommand) changeCommand;
        events.publish(new PreBookingRealized(command.getRequesterId()));
        return null;
    }
}

