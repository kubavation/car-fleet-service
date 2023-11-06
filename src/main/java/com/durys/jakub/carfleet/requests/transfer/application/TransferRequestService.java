package com.durys.jakub.carfleet.requests.transfer.application;

import com.durys.jakub.carfleet.requests.transfer.domain.TransferRequest;
import com.durys.jakub.carfleet.requests.transfer.domain.TransferRequestAssembler;
import com.durys.jakub.carfleet.requests.transfer.domain.TransferRequestRepository;
import com.durys.jakub.carfleet.sharedkernel.cars.CarType;
import com.durys.jakub.carfleet.sharedkernel.requests.RequestId;
import com.durys.jakub.carfleet.sharedkernel.requests.RequesterId;
import com.durys.jakub.carfleet.state.ChangeCommand;
import com.durys.jakub.carfleet.state.State;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class TransferRequestService {

    private final TransferRequestAssembler assembler;
    private final TransferRequestRepository repository;

    public TransferRequest create(RequesterId requesterId, LocalDateTime from, LocalDateTime to, String purpose,
                                  String departure, String destination, CarType carType) {

        TransferRequest transferRequest = new TransferRequest(new RequestId(UUID.randomUUID()), requesterId, from, to, purpose,
                departure, destination, carType);

        State<TransferRequest> result = assembler.configuration().begin(transferRequest);
        return repository.save(result.getObject());
    }


    public TransferRequest change(RequestId requestId, LocalDateTime from, LocalDateTime to,
                                  String purpose, String departure, String destination, CarType carType) {

        TransferRequest transferRequest = repository.load(requestId)
                .orElseThrow(RuntimeException::new);

        State<TransferRequest> result = assembler.configuration()
                .recreate(transferRequest)
                .changeContent(
                        new TransferRequest(transferRequest.requestId(), transferRequest.requesterId(),
                                from, to, purpose, departure, destination, carType, transferRequest.state()));

        return repository.save(result.getObject());
    }


    public Either<List<Exception>, TransferRequest> changeStatus(RequestId requestId, ChangeCommand command) {

        TransferRequest transferRequest = repository.load(requestId)
                .orElseThrow(RuntimeException::new);

        return assembler.configuration()
                .recreate(transferRequest)
                .changeState(command)
                .map(state -> repository.save(state.getObject()));
    }
}
