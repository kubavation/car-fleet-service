package com.durys.jakub.carfleet.plannedevent;

import java.util.UUID;

public interface PlannedEventRepository {
    PlannedEvent load(UUID eventId);
}
