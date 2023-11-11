package com.durys.jakub.carfleet.transfers.domain;d

import com.durys.jakub.carfleet.cars.domain.CarId;
import com.durys.jakub.carfleet.drivers.domain.DriverId;
import com.durys.jakub.carfleet.sharedkernel.requests.RequestId;
import com.durys.jakub.carfleet.sharedkernel.requests.RequesterId;

import java.time.LocalDateTime;
import java.util.UUID;

public class TransferFactory {

    public static Transfer single(RequesterId requesterId, RequestId requestId, String place,
                                  String destination, LocalDateTime from, LocalDateTime to, CarId carId) {

        return new Transfer(new TransferId(UUID.randomUUID()),
                new Destination(place), new TransferPeriod(from, to),
                Transfer.Type.Single, carId, new DriverId(requesterId.value()));
    }

}
