package com.durys.jakub.carfleet.plannedevent;

import java.time.LocalDate;
import java.util.UUID;

public record PlannedEvent(UUID eventId, LocalDate at, String place) {
}
