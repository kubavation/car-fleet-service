package com.durys.jakub.carfleet.transfers.domain;

import com.durys.jakub.carfleet.cars.domain.CarId;
import com.durys.jakub.carfleet.ddd.AggregateId;
import com.durys.jakub.carfleet.ddd.BaseAggregateRoot;
import com.durys.jakub.carfleet.drivers.domain.DriverId;
import com.durys.jakub.carfleet.sharedkernel.requests.RequestId;
import com.durys.jakub.carfleet.state.Flowable;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Table(name = "TRANSFER")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
public class Transfer extends BaseAggregateRoot implements Flowable<Transfer> {

    public enum Type {
        Group, Single
    }

    public enum State {
        OPEN,
        CLOSED,
        ACCEPTED,
        COMPLETED,
        ARCHIVED
    }

    @EmbeddedId
    @AttributeOverride(name = "value", column = @Column(name = "ID"))
    private final TransferId transferId;

    @Embedded
    @AttributeOverride(name = "destination", column = @Column(name = "NAME"))
    private final Destination destination;

    @OneToMany
    @JoinColumn(name = "TRANSFER_ID")
    private final Set<TransferStop> stops;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "TRANSFER_NUMBER"))
    private final TransferNumber transferNumber;

    @Embedded
    private final TransferPeriod period;

    @Enumerated(EnumType.STRING)
    private final Type type;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "CAR_ID"))
    private final CarId carId;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "DRIVER_ID"))
    private final DriverId driverId;

    private String state;

    public Transfer(TransferId transferId, Destination destination, TransferNumber transferNumber,
             TransferPeriod period, Type type, CarId carId, DriverId driverId, String state) {
        this.transferId = transferId;
        this.destination = destination;
        this.transferNumber = transferNumber;
        this.period = period;
        this.type = type;
        this.carId = carId;
        this.driverId = driverId;
        this.state = state;
        this.stops = new HashSet<>();
    }
    public Transfer(TransferId transferId, Destination destination, TransferNumber transferNumber,
                    TransferPeriod period, Type type, CarId carId, DriverId driverId) {
        this.transferId = transferId;
        this.destination = destination;
        this.transferNumber = transferNumber;
        this.period = period;
        this.type = type;
        this.carId = carId;
        this.driverId = driverId;
        this.stops = new HashSet<>();
    }


//    void addParticipant(ParticipantId participantId, String place, RequestId registrationSource) {
//
//        TransferStop transferStop = stops.stream()
//                .filter(stop -> stop.place().equals(place))
//                .findFirst()
//                .orElse(new TransferStop(place, new HashSet<>()));
//
//        transferStop.addParticipant(new TransferParticipant(participantId, registrationSource));
//        stops.add(transferStop);
//    }
//
//    void removeParticipant(ParticipantId participantId, String place, RequestId registrationSource) {
//
//        stops.stream()
//                .filter(stop -> stop.place().equals(place))
//                .findAny()
//                .ifPresent(stop -> {
//                    stop.remove(new TransferParticipant(participantId, registrationSource));
//                    if (stop.participants().isEmpty()) {
//                        stops.remove(stop);
//                    }
//                });
//    }
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


    @Override
    public AggregateId aggregateId() {
        return new AggregateId(transferId.value());
    }

}
