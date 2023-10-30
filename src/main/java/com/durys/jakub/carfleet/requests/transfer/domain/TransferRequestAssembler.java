package com.durys.jakub.carfleet.requests.transfer.domain;

import com.durys.jakub.carfleet.cars.availability.CarAvailabilityService;
import com.durys.jakub.carfleet.requests.transfer.domain.actions.ChangeCarInformation;
import com.durys.jakub.carfleet.requests.transfer.domain.predicates.CarAvailablePredicate;
import com.durys.jakub.carfleet.state.Assembler;
import com.durys.jakub.carfleet.state.StateBuilder;
import com.durys.jakub.carfleet.state.StateConfig;
import org.springframework.stereotype.Component;

import static com.durys.jakub.carfleet.requests.transfer.domain.TransferRequestStatus.*;


@Component
public class TransferRequestAssembler implements Assembler<TransferRequest> {

    private final StateConfig<TransferRequest> configuration;
    private final CarAvailabilityService carAvailabilityService;

    public TransferRequestAssembler(CarAvailabilityService carAvailabilityService) {
        this.carAvailabilityService = carAvailabilityService;
        this.configuration = assemble();
    }

    @Override
    public StateConfig<TransferRequest> configuration() {
        return configuration;
    }

    @Override
    public StateConfig<TransferRequest> assemble() {
        return StateBuilder.builderForClass(TransferRequest.class)
                .beginWith(SUBMITTED)
                .to(ACCEPTED)
                .check(new CarAvailablePredicate(carAvailabilityService))
                .execute(new ChangeCarInformation())
                .and()
                    .from(SUBMITTED).whenContentChangesTo(EDITED)
                .and()
                    .from(EDITED).to(EDITED)
                .and()
                    .from(ACCEPTED).to(CANCELLED)
                .and()
                .from(EDITED)
                    .to(ACCEPTED)
                    .check(new CarAvailablePredicate(carAvailabilityService))
                    .execute(new ChangeCarInformation())
                .and()
                    .from(SUBMITTED).to(REJECTED)
                .build();
    }
}
