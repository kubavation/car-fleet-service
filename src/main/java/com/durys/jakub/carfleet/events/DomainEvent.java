package com.durys.jakub.carfleet.events;

import java.time.Instant;
import java.util.UUID;

public interface DomainEvent {
    UUID id();
    Instant at();
}
