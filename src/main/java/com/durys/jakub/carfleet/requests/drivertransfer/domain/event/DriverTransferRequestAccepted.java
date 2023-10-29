package com.durys.jakub.carfleet.requests.drivertransfer.domain.event;

import com.durys.jakub.carfleet.cars.domain.CarId;
import com.durys.jakub.carfleet.drivers.DriverId;
import com.durys.jakub.carfleet.events.DomainEvent;

import java.time.Instant;
import java.util.UUID;

public class DriverTransferRequestAccepted implements DomainEvent {

    private final UUID id;
    private final Instant at;

    private final CarId carId;
    private final DriverId driverId;

    public DriverTransferRequestAccepted(CarId carId, DriverId driverId) {
        this.id = UUID.randomUUID();
        this.at = Instant.now();
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
