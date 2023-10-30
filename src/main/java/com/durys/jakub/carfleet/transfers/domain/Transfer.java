package com.durys.jakub.carfleet.transfers.domain;

import com.durys.jakub.carfleet.cars.domain.CarId;
import com.durys.jakub.carfleet.drivers.DriverId;
import com.durys.jakub.carfleet.requests.RequestId;
import com.durys.jakub.carfleet.state.Flowable;

import java.time.LocalDateTime;

public class Transfer implements Flowable<Transfer> {

    public enum Type {
        Group, Single
    }

    private final TransferId transferId;
    private final TransferPath path;
    private final TransferNumber transferNumber;
    private final TransferPeriod period;

    private final Type type;

    private final CarId carId;
    private final DriverId driverId;


    private String state;

    public Transfer(TransferId transferId, TransferPath transferPath,
                    TransferPeriod period, CarId carId, DriverId driverId, Type type) {
        this.transferId = transferId;
        this.path = transferPath;
        this.period = period;
        this.carId = carId;
        this.driverId = driverId;
        this.type = type;
        this.transferNumber = new TransferNumber(transferPath, type, period);
    }

    public void addParticipant(ParticipantId participantId, String place, RequestId registrationSource) {
        path.addParticipant(participantId, place, registrationSource);
    }

    public void removeParticipant(ParticipantId participantId, String place, RequestId registrationSource) {
        path.removeParticipant(participantId, place, registrationSource);
    }

    @Override
    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String state() {
        return state;
    }

    @Override
    public Transfer content() {
        return this;
    }

    @Override
    public void setContent(Transfer content) {
        //todo
    }
}
