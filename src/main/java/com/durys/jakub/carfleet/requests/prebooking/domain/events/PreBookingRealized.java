package com.durys.jakub.carfleet.requests.prebooking.domain.events;

import com.durys.jakub.carfleet.events.DomainEvent;
import com.durys.jakub.carfleet.requests.RequesterId;

import java.time.Instant;
import java.util.UUID;

public class PreBookingRealized implements DomainEvent {

    private final UUID id;
    private final Instant at;

    private final RequesterId requesterId; //todo

    public PreBookingRealized(UUID id, Instant at, RequesterId requesterId) {
        this.id = id;
        this.at = at;
        this.requesterId = requesterId;
    }

    public PreBookingRealized(RequesterId requesterId) {
        this.requesterId = requesterId;
        this.id = UUID.randomUUID();
        this.at = Instant.now();
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
