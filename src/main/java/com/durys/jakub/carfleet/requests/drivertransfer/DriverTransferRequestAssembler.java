package com.durys.jakub.carfleet.requests.drivertransfer;

import com.durys.jakub.carfleet.requests.state.Assembler;
import com.durys.jakub.carfleet.requests.state.StateBuilder;
import com.durys.jakub.carfleet.requests.state.StateConfig;
import com.durys.jakub.carfleet.requests.drivertransfer.actions.ChangeDriver;
import com.durys.jakub.carfleet.requests.drivertransfer.predicates.DriverNotEmptyVerifier;
import com.durys.jakub.carfleet.requests.drivertransfer.predicates.RequestContentValidVerifier;
import org.springframework.stereotype.Component;

@Component
public class DriverTransferRequestAssembler implements Assembler<DriverTransferRequest> {

    private static final String NEW = "NEW";
    private static final String EDITED = "EDITED";
    private static final String ACCEPTED = "ACCEPTED";
    private static final String CANCELLED = "CANCELLED";
    private static final String REJECTED = "REJECTED";

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
                    .from(NEW).to(REJECTED).build();
    }
}
