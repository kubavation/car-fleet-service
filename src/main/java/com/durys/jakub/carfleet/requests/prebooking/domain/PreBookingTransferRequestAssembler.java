package com.durys.jakub.carfleet.requests.prebooking.domain;

import com.durys.jakub.carfleet.events.Events;
import com.durys.jakub.carfleet.requests.state.Assembler;
import com.durys.jakub.carfleet.requests.state.StateBuilder;
import com.durys.jakub.carfleet.requests.state.StateConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.durys.jakub.carfleet.requests.prebooking.domain.PreBookingTransferRequestStatus.*;


@Component
@RequiredArgsConstructor
public class PreBookingTransferRequestAssembler implements Assembler<PreBookingTransferRequest> {

    private final Events events;

    @Override
    public StateConfig<PreBookingTransferRequest> assemble() {

        return new StateBuilder<PreBookingTransferRequest>()
                .beginWith(NEW)
                .and()
                    .from(NEW)
                    .whenContentChanged()
                    .to(EDITED)
                .and()
                    .from(EDITED)
                    .whenContentChanged()
                    .to(EDITED)
                .and()
                    .from(NEW).to(CLOSED) //todo action
                .and()
                    .from(EDITED).to(CLOSED) //todo action
                .and()
                    .from(NEW).to(ARCHIVED) //todo action
                .and()
                    .from(EDITED).to(ARCHIVED) //todo action
                .and()
                    .from(CLOSED).to(OPEN) //todo action
                .and()
                    .from(OPEN).to(ARCHIVED)
                .build();
    }
}
