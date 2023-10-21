package com.durys.jakub.carfleet.requests;

import com.durys.jakub.carfleet.requests.state.Assembler;
import com.durys.jakub.carfleet.requests.state.StateBuilder;
import com.durys.jakub.carfleet.requests.state.StateConfig;
import com.durys.jakub.carfleet.requests.state.actions.ChangeDriver;
import com.durys.jakub.carfleet.requests.state.predicates.DriverNotEmptyVerifier;
import com.durys.jakub.carfleet.requests.state.predicates.RequestContentValidVerifier;
import org.springframework.stereotype.Component;

@Component
public class RequestAssembler implements Assembler<Request> {

    private static final String NEW = "NEW";
    private static final String EDITED = "EDITED";
    private static final String ACCEPTED = "ACCEPTED";
    private static final String CANCELLED = "CANCELLED";
    private static final String REJECTED = "REJECTED";

    @Override
    public StateConfig<Request> assemble() {
        return new StateBuilder<Request>()
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
