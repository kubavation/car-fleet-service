package com.durys.jakub.carfleet.requests.transfer.domain.command;

import com.durys.jakub.carfleet.cqrs.Command;
import com.durys.jakub.carfleet.sharedkernel.cars.CarType;
import com.durys.jakub.carfleet.sharedkernel.requests.RequesterId;

import java.time.LocalDateTime;

public record SubmitTransferRequestCommand(RequesterId requesterId, LocalDateTime from, LocalDateTime to,
                                           String purpose, String departure, String destination, CarType carType) implements Command {
}
