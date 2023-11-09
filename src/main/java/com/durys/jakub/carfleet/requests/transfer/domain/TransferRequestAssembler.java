package com.durys.jakub.carfleet.requests.transfer.domain;

import com.durys.jakub.carfleet.cars.availability.CarAvailabilityService;
import com.durys.jakub.carfleet.cars.domain.CarsRepository;
import com.durys.jakub.carfleet.requests.transfer.domain.state.actions.AcceptTransferRequest;
import com.durys.jakub.carfleet.requests.transfer.domain.state.actions.AssignTransferCar;
import com.durys.jakub.carfleet.requests.transfer.domain.state.predicates.CarAssignedPredicate;
import com.durys.jakub.carfleet.requests.transfer.domain.state.predicates.CarValidPredicate;
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
                .to(ASSIGNED)
                    .check(new CarValidPredicate(carAvailabilityService, carsRepository))
                    .execute(new AssignTransferCar(carsRepository))
                .and()
                    .from(SUBMITTED).whenContentChangesTo(EDITED)
                .and()
                    .from(EDITED).whenContentChangesTo(EDITED)
                .and()
                    .from(ACCEPTED).to(CANCELLED) //todo
                .and()
                    .from(EDITED)
                    .to(ASSIGNED)
                    .check(new CarValidPredicate(carAvailabilityService, carsRepository))
                    .execute(new AssignTransferCar(carsRepository))
                .and()
                    .from(ASSIGNED)
                    .to(ACCEPTED)
                    .check(new CarAssignedPredicate())
                    .execute(new AcceptTransferRequest())
                .and()
                    .from(SUBMITTED).to(REJECTED)
                .and()
                    .from(SUBMITTED).to(CANCELLED)
                .and()
                    .from(EDITED).to(REJECTED)
                .and()
                    .from(EDITED).to(CANCELLED)
                .build();
    }
}
