package com.durys.jakub.carfleet.transfers.domain;

import com.durys.jakub.carfleet.cars.domain.CarId;
import com.durys.jakub.carfleet.drivers.DriverId;
import com.durys.jakub.carfleet.requests.RequestId;

import java.time.LocalDateTime;

public class Transfer {

    public enum Type {
        Group, Single
    }


    private final TransferId transferId;
    private final TransferPath transferPath;
    private final TransferNumber transferNumber;

    private final Type type;

    private final CarId carId;
    private final DriverId driverId;

    private final LocalDateTime at;

    public Transfer(TransferId transferId, TransferPath transferPath,
                    CarId carId, DriverId driverId, LocalDateTime at, Type type) {
        this.transferId = transferId;
        this.transferPath = transferPath;

        this.carId = carId;
        this.driverId = driverId;
        this.at = at;
        this.type = type;
        this.transferNumber = new TransferNumber(transferPath, type, at);
    }

    public void addParticipant(ParticipantId participantId, String place, RequestId registrationSource) {
        transferPath.addParticipant(participantId, place, registrationSource);
    }

    public void removeParticipant(ParticipantId participantId, String place, RequestId registrationSource) {
        transferPath.removeParticipant(participantId, place, registrationSource);
    }

}
