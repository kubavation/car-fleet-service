package com.durys.jakub.carfleet.plannedevent.domain;

import java.util.UUID;

public interface PlannedEventRepository {
    PlannedEvent load(UUID eventId);
}
