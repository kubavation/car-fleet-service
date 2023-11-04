package com.durys.jakub.carfleet.requests.transfer.instrastructure.in.events;

import com.durys.jakub.carfleet.sharedkernel.requests.RequesterId;
import com.durys.jakub.carfleet.sharedkernel.cars.CarType;

import java.time.LocalDateTime;

public record TransferRequestSubmitted(RequesterId requesterId, LocalDateTime from, LocalDateTime to, String departure,
                                       String destination, String purpose, CarType carType) {
}
