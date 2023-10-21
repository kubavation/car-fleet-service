package com.durys.jakub.carfleet.requests;

import com.durys.jakub.carfleet.requests.state.StateBuilder;
import com.durys.jakub.carfleet.requests.state.StateConfig;
import com.durys.jakub.carfleet.requests.state.actions.ChangeDriver;
import com.durys.jakub.carfleet.requests.state.predicates.DriverNotEmptyVerifier;
import com.durys.jakub.carfleet.requests.state.predicates.RequestContentValidVerifier;
import org.springframework.stereotype.Component;

@Component
public class RequestAssembler {

    private static final String NEW = "NEW";
    private static final String EDITED = "EDITED";
    private static final String ACCEPTED = "ACCEPTED";
    private static final String CANCELLED = "CANCELLED";
    private static final String REJECTED = "REJECTED";


    public StateConfig assemble() {
        StateBuilder builder = new StateBuilder();
        builder
                .beginWith(NEW)
                .check(new RequestContentValidVerifier());

        builder.from(NEW)
                .whenContentChanged().to(EDITED);
        builder.from(EDITED).whenContentChanged().to(EDITED);
        builder.from(ACCEPTED).to(CANCELLED);
        builder.from(NEW)
                .check(new DriverNotEmptyVerifier())
                .to(ACCEPTED)
                .action(new ChangeDriver());
        builder.from(NEW)
                .to(REJECTED);

        return builder;
    }
}
