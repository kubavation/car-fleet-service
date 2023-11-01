package com.durys.jakub.carfleet.drivers.infrastructure.external;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

public record UserAbsenceConfirmed(UUID id, Instant at, UUID userId, LocalDate from, LocalDate to) {
}
