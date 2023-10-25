package com.durys.jakub.carfleet.requests.drivertransfer.infrastructure.in.event;

import java.time.LocalDateTime;
import java.util.UUID;

public record DriverTransferRequestSubmitted(UUID requesterId, LocalDateTime from, LocalDateTime to, String purpose) {
}
