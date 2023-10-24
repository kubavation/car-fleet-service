package com.durys.jakub.carfleet.requests.drivertransfer.application;

import com.durys.jakub.carfleet.requests.RequestId;
import com.durys.jakub.carfleet.requests.RequesterId;
import com.durys.jakub.carfleet.requests.drivertransfer.domain.DriverTransferRequest;
import com.durys.jakub.carfleet.requests.drivertransfer.domain.DriverTransferRequestAssembler;
import com.durys.jakub.carfleet.requests.drivertransfer.domain.DriverTransferRequestRepository;
import com.durys.jakub.carfleet.requests.state.ChangeCommand;
import com.durys.jakub.carfleet.requests.state.State;
import com.durys.jakub.carfleet.requests.vo.RequestPurpose;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class DriverTransferRequestService {

    private final DriverTransferRequestAssembler assembler;
    private final DriverTransferRequestRepository repository;

    public DriverTransferRequest create(RequesterId requesterId, LocalDateTime from, LocalDateTime to, RequestPurpose purpose) {

        DriverTransferRequest driverTransferRequest = new DriverTransferRequest(new RequestId(UUID.randomUUID()), requesterId, from, to, purpose);

        State<DriverTransferRequest> result = assembler.configuration().begin(driverTransferRequest);
        return repository.save(result.getObject());
    }


    public DriverTransferRequest change(RequestId requestId, LocalDateTime from, LocalDateTime to, RequestPurpose purpose) {

        DriverTransferRequest driverTransferRequest = repository.load(requestId)
                .orElseThrow(RuntimeException::new);

        State<DriverTransferRequest> result = assembler.configuration()
                .recreate(driverTransferRequest)
                .changeContent(
                        new DriverTransferRequest(driverTransferRequest.getRequestId(), driverTransferRequest.getRequesterId(),
                                from, to, purpose, driverTransferRequest.state()));

        return repository.save(result.getObject());
    }


    public DriverTransferRequest changeStatus(RequestId requestId, ChangeCommand command) {

        DriverTransferRequest driverTransferRequest = repository.load(requestId)
                .orElseThrow(RuntimeException::new);

        State<DriverTransferRequest> result = assembler.configuration()
                .recreate(driverTransferRequest)
                .changeState(command);

        return repository.save(result.getObject());
    }
}
