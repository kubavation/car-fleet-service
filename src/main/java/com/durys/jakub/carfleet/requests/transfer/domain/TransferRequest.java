package com.durys.jakub.carfleet.requests.transfer.domain;

import com.durys.jakub.carfleet.cars.domain.CarId;
import com.durys.jakub.carfleet.drivers.DriverId;
import com.durys.jakub.carfleet.requests.Flowable;
import com.durys.jakub.carfleet.requests.RequestId;
import com.durys.jakub.carfleet.requests.RequesterId;
import com.durys.jakub.carfleet.requests.drivertransfer.domain.DriverTransferRequest;
import com.durys.jakub.carfleet.requests.vo.RequestContent;
import com.durys.jakub.carfleet.requests.vo.RequestPurpose;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TransferRequest implements Flowable<TransferRequest> {

    private final RequestId requestId;
    private final RequesterId requesterId;
    private RequestContent content;
    private CarId carId;

    private String state;

    public TransferRequest(RequestId requestId, RequesterId requesterId, RequestContent content) {
        this.requestId = requestId;
        this.requesterId = requesterId;
        this.content = content;
    }

    public TransferRequest(RequestId requestId, RequesterId requesterId,
                           LocalDateTime from, LocalDateTime to, RequestPurpose purpose, String state) {
        this.requestId = requestId;
        this.requesterId = requesterId;
        this.content = new RequestContent(from, to, purpose);
        this.state = state;
    }

    @Override
    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String state() {
        return null;
    }

    @Override
    public TransferRequest content() {
        return null;
    }

    @Override
    public void setContent(TransferRequest request) {
        this.content = new RequestContent(
                request.getContent().getFrom(),
                request.getContent().getTo(),
                request.getContent().getPurpose());
    }
}
