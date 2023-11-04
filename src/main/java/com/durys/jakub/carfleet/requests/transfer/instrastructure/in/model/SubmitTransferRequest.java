package com.durys.jakub.carfleet.requests.transfer.instrastructure.in.model;

import com.durys.jakub.carfleet.sharedkernel.cars.CarType;
import com.fasterxml.jackson.annotation.JsonAutoDetect;

import java.time.LocalDateTime;
import java.util.UUID;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public record SubmitTransferRequest(UUID requesterId, LocalDateTime from, LocalDateTime to, String departure,
                                    String destination, String purpose, CarType carType) {
}

