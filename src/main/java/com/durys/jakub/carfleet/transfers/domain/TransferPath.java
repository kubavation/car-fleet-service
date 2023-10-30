package com.durys.jakub.carfleet.transfers.domain;

import com.durys.jakub.carfleet.requests.RequestId;

import java.util.HashSet;
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

    void addParticipant(ParticipantId participantId, String place, RequestId registrationSource) {

        TransferStop transferStop = stops.stream()
                .filter(stop -> stop.getPlace().equals(place))
                .findFirst()
                .orElse(new TransferStop(place, new HashSet<>()));

        transferStop.addParticipant(new TransferParticipant(participantId, registrationSource));
    }
}
