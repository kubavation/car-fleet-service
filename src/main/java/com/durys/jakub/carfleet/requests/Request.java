package com.durys.jakub.carfleet.requests;

import com.durys.jakub.carfleet.requests.vo.RequestContent;
import com.durys.jakub.carfleet.requests.vo.RequestPurpose;

import java.time.LocalDateTime;

public class Request {

    private final RequestId requestId;
    private final RequesterId requesterId;
    private RequestContent content;

    private String state;

    public void setState(String state) {
        this.state = state;
    }

    public Request(RequestId requestId, RequesterId requesterId,
                   LocalDateTime from, LocalDateTime to, RequestPurpose purpose) {
        this.requestId = requestId;
        this.requesterId = requesterId;
        this.content = new RequestContent(from, to, purpose);
    }

    public void changeCurrentContent(RequestContent content) {
        this.content = new RequestContent(content.getFrom(), content.getTo(), content.getPurpose());
    }

    public String getState() {
        return state;
    }
}
