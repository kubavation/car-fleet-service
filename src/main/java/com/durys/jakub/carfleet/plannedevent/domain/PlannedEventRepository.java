package com.durys.jakub.carfleet.plannedevent.domain;

import reactor.core.publisher.Mono;

import java.util.UUID;

public interface PlannedEventRepository {
    Mono<PlannedEvent> load(UUID eventId);
}
