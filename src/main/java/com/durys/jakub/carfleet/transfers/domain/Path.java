package com.durys.jakub.carfleet.transfers.domain;

import com.durys.jakub.carfleet.sharedkernel.requests.RequestId;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
class Path {

    @Embedded
    @AttributeOverride(name = "name", column = @Column(name = "DESTINATION"))
    private final Destination destination;

    @OneToMany
    @JoinColumn(name = "TRANSFER_ID")
    private final Set<Stop> stops;

    Path(Destination destination, Set<Stop> stops) {
        this.destination = destination;
        this.stops = stops;
    }

    Path(Destination destination) {
        this.destination = destination;
        this.stops = new HashSet<>();
    }

    void addParticipant(ParticipantId participantId, String place, RequestId registrationSource) {
        Stop stop = loadStop(place);
        stop.addParticipant(new TransferParticipant(participantId, registrationSource));
        stops.add(stop);
    }

    void removeParticipant(ParticipantId participantId, String name, RequestId registrationSource) {
        stops().stream()
                .filter(stop -> stop.place().equals(name))
                .findAny()
                .ifPresent(stop -> {
                    stop.removeParticipant(new TransferParticipant(participantId, registrationSource));
                    if (stop.participants().isEmpty()) {
                        stops.remove(stop);
                    }
                });
    }

    Stop loadStop(String name) {
        return stops.stream()
                .filter(stop -> stop.place().equals(name))
                .findFirst()
                .orElse(new Stop(name, new HashSet<>()));
    }

    Destination destination() {
        return destination;
    }

    Set<Stop> stops() {
        return stops;
    }
}
