package com.durys.jakub.carfleet.requests.prebooking.domain;

import com.durys.jakub.carfleet.events.Events;
import com.durys.jakub.carfleet.requests.prebooking.domain.action.RealizePreBookingRequest;
import com.durys.jakub.carfleet.state.Assembler;
import com.durys.jakub.carfleet.state.StateConfig;
import com.durys.jakub.carfleet.state.StateBuilder;
import org.springframework.stereotype.Component;

import static com.durys.jakub.carfleet.requests.prebooking.domain.PreBookingTransferRequestStatus.*;


@Component
public class PreBookingTransferRequestAssembler implements Assembler<PreBookingTransferRequest> {

    private final Events events;
    private final StateConfig<PreBookingTransferRequest> configuration;

    public PreBookingTransferRequestAssembler(Events events) {
        this.events = events;
        this.configuration = assemble();
    }

    @Override
    public StateConfig<PreBookingTransferRequest> configuration() {
        return configuration;
    }

    @Override
    public StateConfig<PreBookingTransferRequest> assemble() {


        return StateBuilder.builderForClass(PreBookingTransferRequest.class)
                .beginWith(NEW).to(ARCHIVED)
                .execute(new RealizePreBookingRequest(events))
                .and()
                    .from(NEW).to(EDITED)
                .and()
                    .from(ARCHIVED).to(ARCHIVED)
                .build();
    }
}
