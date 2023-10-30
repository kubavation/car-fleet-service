package com.durys.jakub.carfleet.transfers.domain;

import java.util.List;

class TransferPath {

    private final String destination;

    private final List<TransferStop> stops;

    public TransferPath(String to, List<TransferStop> stops) {
        this.destination = to;
        this.stops = stops;
    }

    String to() {
        return destination;
    }

    List<TransferStop> getStops() {
        return stops;
    }
}
