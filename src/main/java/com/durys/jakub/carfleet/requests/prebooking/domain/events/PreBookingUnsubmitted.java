package com.durys.jakub.carfleet.requests.prebooking.domain.events;

import com.durys.jakub.carfleet.events.DomainEvent;

import java.time.Instant;
import java.util.UUID;

public class PreBookingUnsubmitted implements DomainEvent {

    private final UUID id;
    private final Instant at;
    private final UUID bookingId; //todo


    public PreBookingUnsubmitted(UUID bookingId) {
        this.id = UUID.randomUUID();
        this.at = Instant.now();
        this.bookingId = bookingId;
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
