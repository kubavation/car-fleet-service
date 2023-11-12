package com.durys.jakub.carfleet.requests.drivertransfer.domain;

import com.durys.jakub.carfleet.cars.domain.CarId;
import com.durys.jakub.carfleet.ddd.AggregateId;
import com.durys.jakub.carfleet.ddd.BaseAggregateRoot;
import com.durys.jakub.carfleet.drivers.domain.DriverId;
import com.durys.jakub.carfleet.plannedevent.PlannedEvent;
import com.durys.jakub.carfleet.state.Flowable;
import com.durys.jakub.carfleet.sharedkernel.requests.RequestId;
import com.durys.jakub.carfleet.sharedkernel.requests.RequesterId;
import lombok.Getter;


@Getter
public class DriverTransferRequest extends BaseAggregateRoot implements Flowable<DriverTransferRequest> {

    public enum Status {
        NEW,
        EDITED,
        ACCEPTED,
        CANCELLED,
        REJECTED
    }

    private final RequestId requestId;
    private final RequesterId requesterId;
    private RequestContent content;

    private String state;

    private DriverId driverId;
    private CarId carId;

    DriverTransferRequest(RequestId requestId, RequesterId requesterId,
                          PlannedEvent event, String departure, DriverId driverId, CarId carId, String state) {
        this.requestId = requestId;
        this.requesterId = requesterId;
        this.content = new RequestContent(event, departure);
        this.driverId = driverId;
        this.carId = carId;
        this.state = state;
    }

    DriverTransferRequest(RequestId requestId, RequesterId requesterId,
                          PlannedEvent event, String departure, DriverId driverId, CarId carId) {
        this(requestId, requesterId, event, departure, driverId, carId, Status.NEW.name());
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
}
