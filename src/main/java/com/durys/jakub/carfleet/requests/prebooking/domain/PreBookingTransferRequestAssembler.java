package com.durys.jakub.carfleet.requests.prebooking.domain;

import com.durys.jakub.carfleet.requests.state.Assembler;
import com.durys.jakub.carfleet.requests.state.StateBuilder;
import com.durys.jakub.carfleet.requests.state.StateConfig;
import org.springframework.stereotype.Component;

import static com.durys.jakub.carfleet.requests.prebooking.domain.PreBookingTransferRequestStatus.*;


@Component
public class PreBookingTransferRequestAssembler implements Assembler<PreBookingTransferRequest> {

    @Override
    public StateConfig<PreBookingTransferRequest> assemble() {
        //return new StateBuilder

        return new StateBuilder<PreBookingTransferRequest>();
    }
}
