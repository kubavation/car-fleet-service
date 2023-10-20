package com.durys.jakub.carfleet.requests;

import java.time.LocalDate;

public class RequestHeader {

    private final RequestId requestId;
    private final RequesterId requesterId;

    private LocalDate from;
    private LocalDate to;

}
