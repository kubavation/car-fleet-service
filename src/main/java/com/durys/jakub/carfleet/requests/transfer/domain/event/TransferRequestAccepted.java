package com.durys.jakub.carfleet.requests.transfer.domain.event;

import com.durys.jakub.carfleet.cars.domain.CarId;
import com.durys.jakub.carfleet.events.DomainEvent;
import com.durys.jakub.carfleet.requests.RequestId;
import com.durys.jakub.carfleet.requests.RequesterId;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

public record TransferRequestAccepted(UUID id, Instant at, RequestId requestId, RequesterId requesterId,
                               LocalDateTime from, LocalDateTime to, String placeFrom,
                               String destination, CarId carId) implements DomainEvent {
}
