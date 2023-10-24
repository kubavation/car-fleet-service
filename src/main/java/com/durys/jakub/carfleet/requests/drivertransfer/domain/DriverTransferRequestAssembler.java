package com.durys.jakub.carfleet.requests.drivertransfer.domain;

import com.durys.jakub.carfleet.requests.drivertransfer.domain.actions.ChangeTransportInformation;
import com.durys.jakub.carfleet.requests.drivertransfer.domain.predicates.DriverNotEmptyVerifier;
import com.durys.jakub.carfleet.requests.state.Assembler;
import com.durys.jakub.carfleet.requests.state.builder.StateBuilder;
import com.durys.jakub.carfleet.requests.state.StateConfig;
import org.springframework.stereotype.Component;

import static com.durys.jakub.carfleet.requests.drivertransfer.domain.DriverTransferRequestStatus.*;


@Component
public class DriverTransferRequestAssembler implements Assembler<DriverTransferRequest> {

    private final StateConfig<DriverTransferRequest> configuration;

    public DriverTransferRequestAssembler() {
        this.configuration = assemble();
    }

    @Override
    public StateConfig<DriverTransferRequest> configuration() {
        return configuration;
    }

    @Override
    public StateConfig<DriverTransferRequest> assemble() {
        return StateBuilder.builderForClass(DriverTransferRequest.class)
                .beginWith(NEW)
                .to(ACCEPTED)
                .check(new DriverNotEmptyVerifier())
                .execute(new ChangeTransportInformation())
                .and()
                    .from(NEW).to(EDITED)  //todo.whenContentChanged().to(EDITED)
                .and()
                    .from(EDITED).to(EDITED)
                .and()
                    .from(ACCEPTED).to(CANCELLED)
                .and()
                .from(EDITED)
                    //.check(new DriverNotEmptyVerifier())
                    .to(ACCEPTED)
                    .execute(new ChangeTransportInformation())
                .and()
                    .from(NEW).to(REJECTED)
                .build();
    }
}
