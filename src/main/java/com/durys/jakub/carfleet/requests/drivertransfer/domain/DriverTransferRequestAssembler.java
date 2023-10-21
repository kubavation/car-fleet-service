package com.durys.jakub.carfleet.requests.drivertransfer.domain;

import com.durys.jakub.carfleet.requests.state.Assembler;
import com.durys.jakub.carfleet.requests.state.StateBuilder;
import com.durys.jakub.carfleet.requests.state.StateConfig;
import com.durys.jakub.carfleet.requests.drivertransfer.domain.actions.ChangeDriver;
import com.durys.jakub.carfleet.requests.drivertransfer.domain.predicates.DriverNotEmptyVerifier;
import com.durys.jakub.carfleet.requests.drivertransfer.domain.predicates.RequestContentValidVerifier;
import org.springframework.stereotype.Component;

import static com.durys.jakub.carfleet.requests.drivertransfer.domain.DriverTransferRequestAssembler.Status.*;

@Component
public class DriverTransferRequestAssembler implements Assembler<DriverTransferRequest> {

    public enum Status {
        NEW,
        EDITED,
        ACCEPTED,
        CANCELLED,
        REJECTED,
    }

    @Override
    public StateConfig<DriverTransferRequest> assemble() {
        return new StateBuilder<DriverTransferRequest>()
                .beginWith(NEW)
                .check(new RequestContentValidVerifier<>())
                .and()
                    .from(NEW).whenContentChanged().to(EDITED)
                .and()
                    .from(EDITED).whenContentChanged().to(EDITED)
                .and()
                    .from(ACCEPTED).to(CANCELLED)
                .and()
                .from(NEW)
                    .check(new DriverNotEmptyVerifier())
                    .to(ACCEPTED)
                    .action(new ChangeDriver())
                .and()
                .from(EDITED)
                    .check(new DriverNotEmptyVerifier())
                    .to(ACCEPTED)
                    .action(new ChangeDriver())
                .and()
                    .from(NEW).to(REJECTED)
                .build();
    }
}
