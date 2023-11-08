package com.durys.jakub.carfleet.requests.prebooking.application;

import com.durys.jakub.carfleet.cars.domain.CarId;
import com.durys.jakub.carfleet.common.errors.ValidationError;
import com.durys.jakub.carfleet.common.errors.ValidationErrors;
import com.durys.jakub.carfleet.drivers.domain.DriverId;
import com.durys.jakub.carfleet.events.Events;
import com.durys.jakub.carfleet.sharedkernel.requests.RequestId;
import com.durys.jakub.carfleet.sharedkernel.requests.RequesterId;
import com.durys.jakub.carfleet.requests.prebooking.domain.PreBookingTransferRequest;
import com.durys.jakub.carfleet.requests.prebooking.domain.PreBookingTransferRequestAssembler;
import com.durys.jakub.carfleet.requests.prebooking.domain.PreBookingTransferRequestRepository;
import com.durys.jakub.carfleet.state.ChangeCommand;
import com.durys.jakub.carfleet.state.State;
import com.durys.jakub.carfleet.requests.vo.RequestPurpose;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class PreBookingTransferRequestService {

    private final PreBookingTransferRequestAssembler assembler;
    private final PreBookingTransferRequestRepository repository;
    private final Events events;

    public PreBookingTransferRequest create(RequesterId requesterId, LocalDateTime from, LocalDateTime to,
                                            RequestPurpose purpose, CarId carId, DriverId driverId) {

        PreBookingTransferRequest request = new PreBookingTransferRequest(new RequestId(UUID.randomUUID()),
                requesterId, from, to, purpose, carId, driverId);

        State<PreBookingTransferRequest> result = assembler.configuration().begin(request);
        return repository.save(result.getObject());
    }


    public Either<ValidationErrors, PreBookingTransferRequest> change(RequestId requestId, LocalDateTime from, LocalDateTime to,
                                        RequestPurpose purpose, CarId carId, DriverId driverId) {

        PreBookingTransferRequest request = repository.load(requestId)
                .orElseThrow(RuntimeException::new);

        return assembler.configuration()
                .recreate(request)
                .changeContent(
                        new PreBookingTransferRequest(
                                request.getRequestId(), request.getRequesterId(), from, to, purpose, carId, driverId))
                .map(state -> repository.save(state.getObject()));
    }


    public Either<ValidationErrors, PreBookingTransferRequest> changeStatus(RequestId requestId, ChangeCommand command) {

        PreBookingTransferRequest request = repository.load(requestId)
                .orElseThrow(RuntimeException::new);

        return assembler.configuration()
                .recreate(request)
                .changeState(command)
                .map(state -> repository.save(state.getObject()));
    }
}
