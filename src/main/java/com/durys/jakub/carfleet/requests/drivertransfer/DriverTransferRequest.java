package com.durys.jakub.carfleet.requests.drivertransfer;

import com.durys.jakub.carfleet.drivers.DriverId;
import com.durys.jakub.carfleet.requests.Flowable;
import com.durys.jakub.carfleet.requests.RequestId;
import com.durys.jakub.carfleet.requests.RequesterId;
import com.durys.jakub.carfleet.requests.drivertransfer.vo.RequestContent;
import com.durys.jakub.carfleet.requests.drivertransfer.vo.RequestPurpose;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DriverTransferRequest implements Flowable<DriverTransferRequest> {

    private final RequestId requestId;
    private final RequesterId requesterId;
    private RequestContent content;

    private String state;

    private DriverId driverId;

    @Override
    public void setState(String state) {
        this.state = state;
    }

    public DriverTransferRequest(RequestId requestId, RequesterId requesterId,
                                 LocalDateTime from, LocalDateTime to, RequestPurpose purpose) {
        this.requestId = requestId;
        this.requesterId = requesterId;
        this.content = new RequestContent(from, to, purpose);
    }

    public DriverTransferRequest(RequestId requestId, RequesterId requesterId,
                                 LocalDateTime from, LocalDateTime to, RequestPurpose purpose, String state) {
        this.requestId = requestId;
        this.requesterId = requesterId;
        this.content = new RequestContent(from, to, purpose);
        this.state = state;
    }

    public void changeCurrentContent(RequestContent content) {
        this.content = new RequestContent(content.getFrom(), content.getTo(), content.getPurpose());
    }

    public String state() {
        return state;
    }

    @Override
    public DriverTransferRequest content() {
        return this;
    }

    @Override
    public void setContent(DriverTransferRequest driverTransferRequest) {
        this.content = new RequestContent(
                driverTransferRequest.content.getFrom(),
                driverTransferRequest.content.getTo(),
                driverTransferRequest.content.getPurpose());
    }

    public DriverId getDriverId() {
        return driverId;
    }

    public void setDriverId(DriverId driverId) {
        this.driverId = driverId;
    }

    public RequestContent getContent() {
        return content;
    }
}
