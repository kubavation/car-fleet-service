package com.durys.jakub.carfleet.requests.prebooking.application;

import com.durys.jakub.carfleet.cars.domain.CarId;
import com.durys.jakub.carfleet.drivers.DriverId;
import com.durys.jakub.carfleet.events.Events;
import com.durys.jakub.carfleet.requests.RequestId;
import com.durys.jakub.carfleet.requests.RequesterId;
import com.durys.jakub.carfleet.requests.prebooking.domain.PreBookingTransferRequest;
import com.durys.jakub.carfleet.requests.prebooking.domain.PreBookingTransferRequestAssembler;
import com.durys.jakub.carfleet.requests.prebooking.domain.PreBookingTransferRequestRepository;
import com.durys.jakub.carfleet.requests.state.ChangeCommand;
import com.durys.jakub.carfleet.requests.state.State;
import com.durys.jakub.carfleet.requests.vo.RequestPurpose;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
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


    public PreBookingTransferRequest change(RequestId requestId, LocalDateTime from, LocalDateTime to,
                                        RequestPurpose purpose, CarId carId, DriverId driverId) {

        PreBookingTransferRequest request = repository.load(requestId)
                .orElseThrow(RuntimeException::new);

        State<PreBookingTransferRequest> result = assembler.configuration()
                .recreate(request)
                .changeContent(
                        new PreBookingTransferRequest(
                                request.getRequestId(), request.getRequesterId(), from, to, purpose, carId, driverId));

        return repository.save(result.getObject());
    }


    public PreBookingTransferRequest changeStatus(RequestId requestId, ChangeCommand command) {

        PreBookingTransferRequest request = repository.load(requestId)
                .orElseThrow(RuntimeException::new);

        State<PreBookingTransferRequest> result = assembler.configuration()
                .recreate(request)
                .changeState(command);

        return repository.save(result.getObject());
    }
}
