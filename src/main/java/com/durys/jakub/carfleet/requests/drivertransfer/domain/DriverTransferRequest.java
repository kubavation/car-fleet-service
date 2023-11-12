package com.durys.jakub.carfleet.requests.drivertransfer.domain;

import com.durys.jakub.carfleet.cars.domain.CarId;
import com.durys.jakub.carfleet.ddd.AggregateId;
import com.durys.jakub.carfleet.ddd.BaseAggregateRoot;
import com.durys.jakub.carfleet.drivers.domain.DriverId;
import com.durys.jakub.carfleet.plannedevent.PlannedEvent;
import com.durys.jakub.carfleet.state.Flowable;
import com.durys.jakub.carfleet.sharedkernel.requests.RequestId;
import com.durys.jakub.carfleet.sharedkernel.requests.RequesterId;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "DRIVER_TRANSFER_REQUEST")
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
public class DriverTransferRequest extends BaseAggregateRoot implements Flowable<DriverTransferRequest> {

    public enum Status {
        NEW,
        EDITED,
        ACCEPTED,
        CANCELLED,
        REJECTED
    }

    @EmbeddedId
    @AttributeOverride(name = "value", column = @Column(name = "ID"))
    private final RequestId requestId;

    @AttributeOverride(name = "value", column = @Column(name = "REQUESTER_ID"))
    private final RequesterId requesterId;

    @Embedded
    private RequestContent content;

    private String state;

    @AttributeOverride(name = "value", column = @Column(name = "ASSIGNED_DRIVER_ID"))
    private DriverId driverId;
    @AttributeOverride(name = "value", column = @Column(name = "ASSIGNED_CAR_ID"))
    private CarId carId;

    public DriverTransferRequest(RequestId requestId, RequesterId requesterId,
                          PlannedEvent event, String departure, DriverId driverId, CarId carId, String state) {
        this.requestId = requestId;
        this.requesterId = requesterId;
        this.content = new RequestContent(event, departure);
        this.driverId = driverId;
        this.carId = carId;
        this.state = state;
    }

    public DriverTransferRequest(RequestId requestId, RequesterId requesterId,
                          PlannedEvent event, String departure, DriverId driverId, CarId carId) {
        this(requestId, requesterId, event, departure, driverId, carId, Status.NEW.name());
    }

    public DriverTransferRequest(RequesterId requesterId, PlannedEvent event, String departure) {
        this(new RequestId(UUID.randomUUID()), requesterId, event, departure, null, null);
    }


    @Override
    public String state() {
        return state;
    }

    @Override
    public DriverTransferRequest content() {
        return this;
    }

    @Override
    public void setState(String state) {
        this.state = state;
    }

    @Override
    public void setContent(DriverTransferRequest driverTransferRequest) {
        this.content = new RequestContent(
                driverTransferRequest.content.plannedEvent(),
                driverTransferRequest.content.departure());
    }

    @Override
    public AggregateId aggregateId() {
        return new AggregateId(requesterId.value());
    }

    public DriverId getDriverId() {
        return driverId;
    }

    public void setDriverId(DriverId driverId) {
        this.driverId = driverId;
    }

    public CarId getCarId() {
        return carId;
    }

    public void setCarId(CarId carId) {
        this.carId = carId;
    }

    public RequestContent getContent() {
        return content;
    }

    public RequestId requestId() {
        return requestId;
    }

    public RequesterId requesterId() {
        return requesterId;
    }
}
