package com.durys.jakub.carfleet.requests.transfer.instrastructure.in.events;

import com.durys.jakub.carfleet.sharedkernel.cars.CarType;

import java.time.LocalDateTime;
import java.util.UUID;

public record TransferRequestEntered(UUID requesterId, LocalDateTime from, LocalDateTime to, String departure,
                                     String destination, String purpose, CarType carType) {
}
