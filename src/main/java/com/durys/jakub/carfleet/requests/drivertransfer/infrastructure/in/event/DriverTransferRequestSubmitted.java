package com.durys.jakub.carfleet.requests.drivertransfer.infrastructure.in.event;

import com.durys.jakub.carfleet.cars.domain.CarId;
import com.durys.jakub.carfleet.drivers.domain.DriverId;
import com.durys.jakub.carfleet.plannedevent.PlannedEventId;

import java.time.LocalDateTime;
import java.util.UUID;

public record DriverTransferRequestSubmitted(UUID requesterId, PlannedEventId eventId, String departure) {
}
