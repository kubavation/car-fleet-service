package com.durys.jakub.carfleet.transfers.domain;

import com.durys.jakub.carfleet.cars.domain.CarId;
import com.durys.jakub.carfleet.drivers.domain.DriverId;
import com.durys.jakub.carfleet.sharedkernel.requests.RequesterId;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TransferFactory {

    public static Transfer single(RequesterId requesterId, String destination,
                                  LocalDateTime from, LocalDateTime to, CarId carId) {

        return new Transfer(new TransferId(UUID.randomUUID()),
                new Destination(destination), new Period(from, to),
                Transfer.Type.Single, carId, new DriverId(requesterId.value()));
    }

    public static Transfer group(RequesterId requesterId, String destination,
                                  LocalDateTime from, LocalDateTime to, CarId carId, DriverId driverId) {

        return new Transfer(new TransferId(UUID.randomUUID()), new Destination(destination), new Period(from, to),
                Transfer.Type.Group, carId, driverId);
    }

}
