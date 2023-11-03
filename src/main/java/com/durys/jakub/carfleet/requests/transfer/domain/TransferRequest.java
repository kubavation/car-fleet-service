package com.durys.jakub.carfleet.requests.transfer.domain;

import com.durys.jakub.carfleet.cars.domain.Car;
import com.durys.jakub.carfleet.cars.domain.CarId;
import com.durys.jakub.carfleet.common.errors.ValidationError;
import com.durys.jakub.carfleet.sharedkernel.cars.CarType;
import com.durys.jakub.carfleet.state.Flowable;
import com.durys.jakub.carfleet.requests.RequestId;
import com.durys.jakub.carfleet.requests.RequesterId;
import com.durys.jakub.carfleet.requests.vo.RequestPurpose;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TransferRequest implements Flowable<TransferRequest> {

    private final RequestId requestId;
    private final RequesterId requesterId;
    private RequestContent content;
    private CarId assignedCar;

    private String state;

    public TransferRequest(RequestId requestId, RequesterId requesterId,
                           LocalDateTime from, LocalDateTime to, String purpose,
                           String departure, String destination, CarType carType, String state) {
        this.requestId = requestId;
        this.requesterId = requesterId;
        this.content = new RequestContent(from, to, new RequestPurpose(purpose), departure, destination, carType);
        this.state = state;
    }

    public TransferRequest(RequestId requestId, RequesterId requesterId,
                           LocalDateTime from, LocalDateTime to, String purpose,
                           String departure, String destination, CarType carType) {
        this.requestId = requestId;
        this.requesterId = requesterId;
        this.content = new RequestContent(from, to, new RequestPurpose(purpose), departure, destination, carType);
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
}
