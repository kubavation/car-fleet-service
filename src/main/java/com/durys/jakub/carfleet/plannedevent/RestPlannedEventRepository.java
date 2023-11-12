package com.durys.jakub.carfleet.plannedevent;

import lombok.RequiredArgsConstructor;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
class RestPlannedEventRepository implements PlannedEventRepository {

    private final WebClient webClient;

    @Override
    public Mono<PlannedEvent> load(PlannedEventId id) {
        return Mono.empty();
    }
}
