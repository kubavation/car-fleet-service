package com.durys.jakub.carfleet.requests.transfer.application;

import com.durys.jakub.carfleet.common.errors.ValidationErrorHandlers;
import com.durys.jakub.carfleet.common.errors.ValidationErrors;
import com.durys.jakub.carfleet.requests.transfer.domain.TransferRequest;
import com.durys.jakub.carfleet.requests.transfer.domain.TransferRequestAssembler;
import com.durys.jakub.carfleet.requests.transfer.domain.TransferRequestRepository;
import com.durys.jakub.carfleet.requests.transfer.domain.command.SubmitTransferRequestCommand;
import com.durys.jakub.carfleet.sharedkernel.cars.CarType;
import com.durys.jakub.carfleet.sharedkernel.identity.IdentityProvider;
import com.durys.jakub.carfleet.sharedkernel.requests.RequestId;
import com.durys.jakub.carfleet.state.ChangeCommand;
import com.durys.jakub.carfleet.state.State;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class TransferRequestService {

    private final TransferRequestAssembler assembler;
    private final TransferRequestRepository repository;
    private final IdentityProvider<UUID> identityProvider;

    public Either<ValidationErrors, TransferRequest> handle(SubmitTransferRequestCommand command) {



        var errorHandler = ValidationErrorHandlers.aggregatingValidationErrorHandler();

        TransferRequest.test(command.from(), command.to(), command.purpose(),
                command.departure(), command.destination(), command.carType(), errorHandler);

        if (errorHandler.hasErrors()) {
            return Either.left(errorHandler.errors());
        }

        TransferRequest transferRequest = new TransferRequest(new RequestId(identityProvider.generate()),
                command.requesterId(), command.from(), command.to(), command.purpose(),
                command.departure(), command.destination(), command.carType());

        State<TransferRequest> result = assembler.configuration()
                .begin(transferRequest);

        return Either.right(repository.save(result.getObject()));
    }


    public Either<ValidationErrors, TransferRequest> change(RequestId requestId, LocalDateTime from, LocalDateTime to,
                                  String purpose, String departure, String destination, CarType carType) {

        TransferRequest transferRequest = repository.load(requestId)
                .orElseThrow(RuntimeException::new);

        var errorHandler = ValidationErrorHandlers.aggregatingValidationErrorHandler();

        TransferRequest.test(from, to, purpose, departure, destination, carType, errorHandler);

        if (errorHandler.hasErrors()) {
            return Either.left(errorHandler.errors());
        }

        State<TransferRequest> result = assembler.configuration()
                .recreate(transferRequest)
                .changeContent(
                        new TransferRequest(transferRequest.requestId(), transferRequest.requesterId(),
                                from, to, purpose, departure, destination, carType, transferRequest.state()));

        return Either.right(repository.save(result.getObject()));
    }


    public Either<ValidationErrors, TransferRequest> changeStatus(RequestId requestId, ChangeCommand command) {

        TransferRequest transferRequest = repository.load(requestId)
                .orElseThrow(RuntimeException::new);

        return assembler.configuration()
                .recreate(transferRequest)
                .changeState(command)
                .map(state -> repository.save(state.getObject()));
    }
}
