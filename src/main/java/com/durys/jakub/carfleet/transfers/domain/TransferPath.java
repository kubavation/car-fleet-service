package com.durys.jakub.carfleet.transfers.domain;

import com.durys.jakub.carfleet.requests.RequestId;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

class TransferPath {

    private final String destination;

    private final Set<TransferStop> stops;

    public TransferPath(String to, Set<TransferStop> stops) {
        this.destination = to;
        this.stops = stops;
    }

    public TransferPath(String from, String to, TransferParticipant participant) {
        this.destination = to;
        this.stops = new HashSet<>(Arrays.asList(new TransferStop(from, Set.of(participant))));
    }

    String to() {
        return destination;
    }

    Set<TransferStop> getStops() {
        return stops;
    }

    void addParticipant(ParticipantId participantId, String place, RequestId registrationSource) {

        TransferStop transferStop = stops.stream()
                .filter(stop -> stop.place().equals(place))
                .findFirst()
                .orElse(new TransferStop(place, new HashSet<>()));

        transferStop.addParticipant(new TransferParticipant(participantId, registrationSource));
        stops.add(transferStop);
    }

    void removeParticipant(ParticipantId participantId, String place, RequestId registrationSource) {

        stops.stream()
            .filter(stop -> stop.place().equals(place))
            .findAny()
                .ifPresent(stop -> {
                    stop.remove(new TransferParticipant(participantId, registrationSource));
                    if (stop.participants().isEmpty()) {
                        stops.remove(stop);
                    }
                });
    }
}
