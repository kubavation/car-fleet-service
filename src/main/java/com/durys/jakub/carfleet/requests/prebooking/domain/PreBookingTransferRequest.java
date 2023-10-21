package com.durys.jakub.carfleet.requests.prebooking.domain;

import com.durys.jakub.carfleet.cars.domain.CarId;
import com.durys.jakub.carfleet.drivers.DriverId;
import com.durys.jakub.carfleet.requests.Flowable;
import com.durys.jakub.carfleet.requests.RequestId;
import com.durys.jakub.carfleet.requests.RequesterId;
import com.durys.jakub.carfleet.requests.vo.RequestPurpose;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PreBookingTransferRequest implements Flowable<PreBookingTransferRequest> {

    private final RequestId requestId;
    private final RequesterId requesterId;

    private PreBookingRequestContent content;

    private String state;


    public PreBookingTransferRequest(RequestId requestId, RequesterId requesterId,
                                     LocalDateTime from, LocalDateTime to, RequestPurpose purpose, CarId carId, DriverId driverId) {
        this.requestId = requestId;
        this.requesterId = requesterId;
        this.content = new PreBookingRequestContent(from, to, purpose, carId, driverId);
    }

    public PreBookingTransferRequest(RequestId requestId, RequesterId requesterId, LocalDateTime from, LocalDateTime to,
                                     RequestPurpose purpose, String state, CarId carId, DriverId driverId) {
        this.requestId = requestId;
        this.requesterId = requesterId;
        this.content = new PreBookingRequestContent(from, to, purpose, carId, driverId);
        this.state = state;
    }


    @Override
    public String state() {
        return state;
    }

    @Override
    public PreBookingTransferRequest content() {
        return this;
    }

    @Override
    public void setContent(PreBookingTransferRequest request) {
        this.content = new PreBookingRequestContent(
                request.content.getFrom(), request.content.getTo(),
                request.content.getPurpose(), request.content.getCarId(), request.content.getDriverId());
    }
}
