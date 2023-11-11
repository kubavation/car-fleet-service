package com.durys.jakub.carfleet.transfers.domain;

import com.durys.jakub.carfleet.sharedkernel.requests.RequestId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "TRANSFER_PATH")
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
class TransferPath {

    private final Set<TransferStop> stops;

    public TransferPath(Set<TransferStop> stops) {
        this.stops = stops;
    }

    public TransferPath(String from, String to, TransferParticipant participant) {
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
