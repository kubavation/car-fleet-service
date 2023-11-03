package com.durys.jakub.carfleet.requests.transfer.domain.event;

import com.durys.jakub.carfleet.cars.domain.CarId;
import com.durys.jakub.carfleet.events.DomainEvent;
import com.durys.jakub.carfleet.requests.RequestId;
import com.durys.jakub.carfleet.requests.RequesterId;
import com.durys.jakub.carfleet.requests.transfer.domain.TransferRequest;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

public record TransferRequestAccepted(UUID id, Instant at, RequestId requestId, RequesterId requesterId,
                               LocalDateTime from, LocalDateTime to, String departure,
                               String destination, CarId carId) implements DomainEvent {

    public static TransferRequestAccepted from(TransferRequest request) {
        return new TransferRequestAccepted(
                UUID.randomUUID(), Instant.now(),
                request.getRequestId(), request.getRequesterId(), request.transferFrom(),
                request.transferTo(), request.departure(), request.destination(), request.assignedCar());
    }
}
