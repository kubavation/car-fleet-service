package com.durys.jakub.carfleet.requests.transfer.domain.event;

import com.durys.jakub.carfleet.cars.domain.CarId;
import com.durys.jakub.carfleet.events.DomainEvent;
import com.durys.jakub.carfleet.sharedkernel.cars.CarType;
import com.durys.jakub.carfleet.sharedkernel.requests.RequestId;
import com.durys.jakub.carfleet.sharedkernel.requests.RequesterId;
import com.durys.jakub.carfleet.requests.transfer.domain.TransferRequest;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

public record TransferRequestAccepted(UUID id, Instant at, RequestId requestId, RequesterId requesterId,
                                      LocalDateTime from, LocalDateTime to, String departure,
                                      String destination, CarId carId, CarType carType) implements DomainEvent {

    public TransferRequestAccepted(TransferRequest request) {
        this(UUID.randomUUID(), Instant.now(), request.requestId(), request.requesterId(), request.transferFrom(),
                request.transferTo(), request.departure(), request.destination(), request.assignedCar(), request.carType());
    }
}
