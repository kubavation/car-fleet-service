package com.durys.jakub.carfleet.plannedevent;

import reactor.core.publisher.Mono;


public interface PlannedEventRepository {
    Mono<PlannedEvent> load(PlannedEventId id);
}
