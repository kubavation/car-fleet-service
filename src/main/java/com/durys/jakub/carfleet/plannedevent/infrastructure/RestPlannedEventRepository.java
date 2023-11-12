package com.durys.jakub.carfleet.plannedevent.infrastructure;

import com.durys.jakub.carfleet.plannedevent.domain.PlannedEvent;
import com.durys.jakub.carfleet.plannedevent.domain.PlannedEventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RequiredArgsConstructor
class RestPlannedEventRepository implements PlannedEventRepository {

    private final WebClient webClient;

    @Override
    public Mono<PlannedEvent> load(UUID eventId) {
        return Mono.empty();
    }
}
