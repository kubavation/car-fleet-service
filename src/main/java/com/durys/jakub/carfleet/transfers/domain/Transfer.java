package com.durys.jakub.carfleet.transfers.domain;

import com.durys.jakub.carfleet.cars.domain.CarId;
import com.durys.jakub.carfleet.drivers.DriverId;
import com.durys.jakub.carfleet.requests.RequestId;

import java.time.LocalDateTime;
import java.util.Set;

public class Transfer {

    private final TransferId transferId;
    private final TransferPath transferPath;
    private final TransferNumber transferNumber;

    private final CarId carId;
    private final DriverId driverId;

    private final LocalDateTime at;

    public Transfer(TransferId transferId, TransferPath transferPath,
                    CarId carId, DriverId driverId, LocalDateTime at) {
        this.transferId = transferId;
        this.transferPath = transferPath;

        this.carId = carId;
        this.driverId = driverId;
        this.at = at;
        this.transferNumber = new TransferNumber(transferPath, at);
    }

    void addParticipant(ParticipantId participantId, String name, RequestId registrationSource) {
        transferPath.
    }
}
