package com.durys.jakub.carfleet.requests.drivertransfer.application;

import com.durys.jakub.carfleet.sharedkernel.requests.RequestId;
import com.durys.jakub.carfleet.sharedkernel.requests.RequesterId;
import com.durys.jakub.carfleet.requests.drivertransfer.domain.DriverTransferRequest;
import com.durys.jakub.carfleet.requests.drivertransfer.domain.DriverTransferRequestAssembler;
import com.durys.jakub.carfleet.requests.drivertransfer.domain.DriverTransferRequestRepository;
import com.durys.jakub.carfleet.state.ChangeCommand;
import com.durys.jakub.carfleet.state.State;
import com.durys.jakub.carfleet.requests.vo.RequestPurpose;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class DriverTransferRequestService {

    private final DriverTransferRequestAssembler assembler;
    private final DriverTransferRequestRepository repository;


    public DriverTransferRequest create(RequesterId requesterId, LocalDateTime from, LocalDateTime to, RequestPurpose purpose,
                                        String departure, String destination) {

        DriverTransferRequest driverTransferRequest
                = new DriverTransferRequest(new RequestId(UUID.randomUUID()), requesterId, from, to, purpose, departure, destination);

        State<DriverTransferRequest> result = assembler.configuration().begin(driverTransferRequest);
        return repository.save(result.getObject());
    }


    public DriverTransferRequest change(RequestId requestId, LocalDateTime from, LocalDateTime to, RequestPurpose purpose,
                                        String departure, String destination) {

        DriverTransferRequest driverTransferRequest = repository.load(requestId)
                .orElseThrow(RuntimeException::new);

        State<DriverTransferRequest> result = assembler.configuration()
                .recreate(driverTransferRequest)
                .changeContent(
                        new DriverTransferRequest(driverTransferRequest.getRequestId(), driverTransferRequest.getRequesterId(),
                                from, to, purpose, departure, destination, driverTransferRequest.state()));

        return repository.save(result.getObject());
    }


    public Either<Set<Exception>, DriverTransferRequest> changeStatus(RequestId requestId, ChangeCommand command) {

        DriverTransferRequest driverTransferRequest = repository.load(requestId)
                .orElseThrow(RuntimeException::new);

        return assembler.configuration()
                .recreate(driverTransferRequest)
                .changeState(command)
                .map(state -> repository.save(state.getObject()));
    }
}
