package com.durys.jakub.carfleet.requests.prebooking.domain.events;

import com.durys.jakub.carfleet.cars.domain.CarId;
import com.durys.jakub.carfleet.drivers.domain.DriverId;
import com.durys.jakub.carfleet.events.DomainEvent;
import com.durys.jakub.carfleet.requests.RequestId;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

public class PreBookingSubmitted implements DomainEvent {

    private final UUID id;
    private final Instant at;
    private final RequestId requestId;
    private final LocalDateTime from;
    private final LocalDateTime to;
    private final CarId carId;
    private final DriverId driverId;

    public PreBookingSubmitted( UUID id, Instant at, RequestId requestId,
                                LocalDateTime from, LocalDateTime to, CarId carId, DriverId driverId) {
        this.id = id;
        this.at = at;
        this.requestId = requestId;
        this.from = from;
        this.to = to;
        this.carId = carId;
        this.driverId = driverId;
    }

    public PreBookingSubmitted(RequestId requestId, LocalDateTime from, LocalDateTime to, CarId carId, DriverId driverId) {
        this.id = UUID.randomUUID();
        this.at = Instant.now();
        this.requestId = requestId;
        this.from = from;
        this.to = to;
        this.carId = carId;
        this.driverId = driverId;
    }

    @Override
    public UUID id() {
        return id;
    }

    @Override
    public Instant at() {
        return at;
    }
}
