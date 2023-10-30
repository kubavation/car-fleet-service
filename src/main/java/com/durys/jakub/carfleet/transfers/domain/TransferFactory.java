package com.durys.jakub.carfleet.transfers.domain;

import com.durys.jakub.carfleet.cars.domain.CarId;
import com.durys.jakub.carfleet.drivers.DriverId;
import com.durys.jakub.carfleet.requests.RequestId;
import com.durys.jakub.carfleet.requests.RequesterId;

import java.time.LocalDateTime;
import java.util.UUID;

public class TransferFactory {

    public static Transfer singleTransfer(RequesterId requesterId, RequestId requestId, String place,
                                          String destination, LocalDateTime from, LocalDateTime to, CarId carId) {
        return new Transfer(
                new TransferId(UUID.randomUUID()),
                new TransferPath(place, destination,
                        new TransferParticipant(
                                new ParticipantId(requesterId.value()), requestId)),
                new TransferPeriod(from, to),
                carId,
                new DriverId(requesterId.value()),
                Transfer.Type.Single);
    }

}
