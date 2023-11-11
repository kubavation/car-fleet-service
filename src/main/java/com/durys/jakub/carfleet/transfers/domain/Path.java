package com.durys.jakub.carfleet.transfers.domain;

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

    Destination destination() {
        return destination;
    }

    Set<Stop> stops() {
        return stops;
    }
}
