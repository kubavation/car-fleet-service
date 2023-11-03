package com.durys.jakub.carfleet.requests.transfer.domain;

import com.durys.jakub.carfleet.cars.domain.CarId;
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
                           String departure, String destination, String state) {
        this.requestId = requestId;
        this.requesterId = requesterId;
        this.content = new RequestContent(from, to, new RequestPurpose(purpose), departure, destination);
        this.state = state;
    }

    public TransferRequest(RequestId requestId, RequesterId requesterId,
                           LocalDateTime from, LocalDateTime to, String purpose,
                           String departure, String destination) {
        this.requestId = requestId;
        this.requesterId = requesterId;
        this.content = new RequestContent(from, to, new RequestPurpose(purpose), departure, destination);
    }

    public void setUpCar(CarId carId) {
        this.assignedCar = carId;
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
                request.getContent().from(),
                request.getContent().to(),
                request.getContent().purpose(),
                request.getContent().departure(),
                request.getContent().destination());
    }
}
