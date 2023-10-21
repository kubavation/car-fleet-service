package com.durys.jakub.carfleet.requests;

import com.durys.jakub.carfleet.drivers.DriverId;
import com.durys.jakub.carfleet.requests.vo.RequestContent;
import com.durys.jakub.carfleet.requests.vo.RequestPurpose;

import java.time.LocalDateTime;

public class Request implements Flowable {

    private final RequestId requestId;
    private final RequesterId requesterId;
    private RequestContent content;

    private String state;

    private DriverId driverId;

    @Override
    public void setState(String state) {
        this.state = state;
    }

    public Request(RequestId requestId, RequesterId requesterId,
                   LocalDateTime from, LocalDateTime to, RequestPurpose purpose) {
        this.requestId = requestId;
        this.requesterId = requesterId;
        this.content = new RequestContent(from, to, purpose);
    }

    public Request(RequestId requestId, RequesterId requesterId,
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
