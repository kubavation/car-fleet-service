package com.durys.jakub.carfleet.requests.transfer.domain.command;

import com.durys.jakub.carfleet.sharedkernel.cars.CarType;
import com.durys.jakub.carfleet.sharedkernel.requests.RequestId;

import java.time.LocalDateTime;

public record ChangeTransferRequestContentCommand(RequestId requestId, LocalDateTime from, LocalDateTime to,
                                                  String purpose, String departure, String destination, CarType carType) {
}
