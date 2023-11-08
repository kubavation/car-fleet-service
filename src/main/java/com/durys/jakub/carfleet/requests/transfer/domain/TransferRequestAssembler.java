package com.durys.jakub.carfleet.requests.transfer.domain;

import com.durys.jakub.carfleet.cars.availability.CarAvailabilityService;
import com.durys.jakub.carfleet.cars.domain.CarsRepository;
import com.durys.jakub.carfleet.requests.transfer.domain.state.actions.AcceptTransferRequest;
import com.durys.jakub.carfleet.requests.transfer.domain.state.actions.AssignTransferCar;
import com.durys.jakub.carfleet.requests.transfer.domain.state.predicates.CarAssignedPredicate;
import com.durys.jakub.carfleet.requests.transfer.domain.state.predicates.CarAvailablePredicate;
import com.durys.jakub.carfleet.state.Assembler;
import com.durys.jakub.carfleet.state.StateBuilder;
import com.durys.jakub.carfleet.state.StateConfig;
import org.springframework.stereotype.Component;

import static com.durys.jakub.carfleet.requests.transfer.domain.TransferRequest.Status.*;


@Component
public class TransferRequestAssembler implements Assembler<TransferRequest> {

    private final StateConfig<TransferRequest> configuration;
    private final CarAvailabilityService carAvailabilityService;
    private final CarsRepository carsRepository;

    public TransferRequestAssembler(CarAvailabilityService carAvailabilityService,
                                    CarsRepository carsRepository) {
        this.carAvailabilityService = carAvailabilityService;
        this.carsRepository = carsRepository;
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
                .check(new CarAssignedPredicate())
                .check(new CarAvailablePredicate(carAvailabilityService))
                .execute(new AcceptTransferRequest())
                .and()
                    .from(SUBMITTED)
                    .whenContentChangesTo(EDITED)
                    .check(new CarAvailablePredicate(carAvailabilityService))
                .and()
                    .from(EDITED).to(EDITED).check(new CarAvailablePredicate(carAvailabilityService))
                .and()
                    .from(ACCEPTED).to(CANCELLED) //todo
                .and()
                .from(EDITED)
                    .to(ACCEPTED)
                    .check(new CarAssignedPredicate())
                    .check(new CarAvailablePredicate(carAvailabilityService))
                    .execute(new AcceptTransferRequest())
                .and()
                    .from(SUBMITTED).to(REJECTED)
                .and()
                    .from(EDITED).to(REJECTED)
                .and()
                    .from(EDITED).to(CANCELLED)
                .build();
    }
}
