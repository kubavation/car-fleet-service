package com.durys.jakub.carfleet.requests.drivertransfer.application;

import com.durys.jakub.carfleet.common.errors.ValidationErrors;
import com.durys.jakub.carfleet.plannedevent.PlannedEvent;
import com.durys.jakub.carfleet.plannedevent.PlannedEventId;
import com.durys.jakub.carfleet.plannedevent.PlannedEventRepository;
import com.durys.jakub.carfleet.requests.drivertransfer.domain.DriverTransferRequest;
import com.durys.jakub.carfleet.requests.drivertransfer.domain.DriverTransferRequestAssembler;
import com.durys.jakub.carfleet.requests.drivertransfer.domain.DriverTransferRequestRepository;
import com.durys.jakub.carfleet.sharedkernel.requests.RequestId;
import com.durys.jakub.carfleet.sharedkernel.requests.RequesterId;
import com.durys.jakub.carfleet.state.ChangeCommand;
import com.durys.jakub.carfleet.state.State;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DriverTransferRequestService {

    private final DriverTransferRequestAssembler assembler;
    private final DriverTransferRequestRepository repository;
    private final PlannedEventRepository plannedEventRepository;


    public DriverTransferRequest create(RequesterId requesterId, PlannedEventId eventId, String departure) {

        PlannedEvent plannedEvent = plannedEventRepository.load(eventId).block();

        DriverTransferRequest driverTransferRequest = new DriverTransferRequest(requesterId, plannedEvent, departure);

        State<DriverTransferRequest> result = assembler.configuration().begin(driverTransferRequest);
        return repository.save(result.getObject());
    }


    public Either<ValidationErrors, DriverTransferRequest> change(RequestId requestId, PlannedEventId eventId, String departure) {

        DriverTransferRequest driverTransferRequest = repository.load(requestId)
                .orElseThrow(RuntimeException::new);

        PlannedEvent plannedEvent = plannedEventRepository.load(eventId).block();

        return assembler.configuration()
                .recreate(driverTransferRequest)
                .changeContent(
                        new DriverTransferRequest(driverTransferRequest.requestId(),
                                driverTransferRequest.requesterId(), plannedEvent, departure, null, null))
                .map(state -> repository.save(state.getObject()));
    }


    public Either<ValidationErrors, DriverTransferRequest> changeStatus(RequestId requestId, ChangeCommand command) {

        DriverTransferRequest driverTransferRequest = repository.load(requestId)
                .orElseThrow(RuntimeException::new);

        return assembler.configuration()
                .recreate(driverTransferRequest)
                .changeState(command)
                .map(state -> repository.save(state.getObject()));
    }
}
