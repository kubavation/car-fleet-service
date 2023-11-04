package com.durys.jakub.carfleet.requests.transfer.domain;

import com.durys.jakub.carfleet.cars.domain.Car;
import com.durys.jakub.carfleet.cars.domain.CarId;
import com.durys.jakub.carfleet.common.errors.ValidationError;
import com.durys.jakub.carfleet.ddd.BaseAggregateRoot;
import com.durys.jakub.carfleet.events.DomainEvent;
import com.durys.jakub.carfleet.requests.vo.RequestPurpose;
import com.durys.jakub.carfleet.sharedkernel.cars.CarType;
import com.durys.jakub.carfleet.sharedkernel.requests.RequestId;
import com.durys.jakub.carfleet.sharedkernel.requests.RequesterId;
import com.durys.jakub.carfleet.state.Flowable;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "TRANSFER_REQUEST")
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
public class TransferRequest extends BaseAggregateRoot implements Flowable<TransferRequest> {

    public enum Status {
        SUBMITTED,
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

    @AttributeOverride(name = "value", column = @Column(name = "ASSIGNED_CAR_ID"))
    private CarId assignedCar;

    private String state;

    public TransferRequest(RequestId requestId, RequesterId requesterId,
                           LocalDateTime from, LocalDateTime to, String purpose,
                           String departure, String destination, CarType carType, String state) {
        this.requestId = requestId;
        this.requesterId = requesterId;
        this.content = new RequestContent(from, to, new RequestPurpose(purpose), departure, destination, carType);
        this.state = state;
        this.events = new HashSet<>();
    }

    public TransferRequest(RequestId requestId, RequesterId requesterId,
                           LocalDateTime from, LocalDateTime to, String purpose,
                           String departure, String destination, CarType carType) {
        this.requestId = requestId;
        this.requesterId = requesterId;
        this.content = new RequestContent(from, to, new RequestPurpose(purpose), departure, destination, carType);
        this.events = new HashSet<>();
    }

    public void assign(Car car) {

        if (car.getCarType() != content.carType()) {
            throw new ValidationError("Invalid assigned car type");
        }

        this.assignedCar = car.getCarId();
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
    public TransferRequest content() {
        return this;
    }

    @Override
    public void setContent(TransferRequest request) {
        this.content = new RequestContent(
                request.content.from(),
                request.content.to(),
                request.content.purpose(),
                request.content.departure(),
                request.content.destination(),
                request.content.carType());
    }


    public LocalDateTime transferFrom() {
        return content.from();
    }

    public LocalDateTime transferTo() {
        return content.to();
    }

    public RequestPurpose purpose() {
        return content.purpose();
    }

    public String departure() {
        return content.departure();
    }

    public String destination() {
        return content.destination();
    }

    public CarType carType() {
        return content.carType();
    }

    public CarId assignedCar() {
        return assignedCar;
    }

    public Set<DomainEvent> events() {
        return events;
    }

    public RequestId requestId() {
        return requestId;
    }

    public RequesterId requesterId() {
        return requesterId;
    }
}
