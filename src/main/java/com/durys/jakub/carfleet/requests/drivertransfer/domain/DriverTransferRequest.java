package com.durys.jakub.carfleet.requests.drivertransfer.domain;

import com.durys.jakub.carfleet.cars.domain.CarId;
import com.durys.jakub.carfleet.drivers.domain.DriverId;
import com.durys.jakub.carfleet.state.Flowable;
import com.durys.jakub.carfleet.sharedkernel.requests.RequestId;
import com.durys.jakub.carfleet.sharedkernel.requests.RequesterId;
import com.durys.jakub.carfleet.requests.vo.RequestContent;
import com.durys.jakub.carfleet.requests.vo.RequestPurpose;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DriverTransferRequest implements Flowable<DriverTransferRequest> {

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

    public DriverTransferRequest(RequestId requestId, RequesterId requesterId,
                                 LocalDateTime from, LocalDateTime to, RequestPurpose purpose, String departure,
                                 String destination) {
        this(requestId, requesterId, from, to, purpose, departure, destination, Status.NEW.name());
    }

    public DriverTransferRequest(RequestId requestId, RequesterId requesterId,
                                 LocalDateTime from, LocalDateTime to, RequestPurpose purpose,
                                 String departure, String destination, String state) {
        this.requestId = requestId;
        this.requesterId = requesterId;
        this.content = new RequestContent(from, to, purpose, departure, destination);
        this.state = state;
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
                driverTransferRequest.content.getFrom(),
                driverTransferRequest.content.getTo(),
                driverTransferRequest.content.getPurpose(),
                driverTransferRequest.content.getDeparture(),
                driverTransferRequest.content.getDestination());
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
