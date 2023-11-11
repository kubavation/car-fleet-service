package com.durys.jakub.carfleet.transfers.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
class TransferPath {

    @Embedded
    @AttributeOverride(name = "name", column = @Column(name = "DESTINATION"))
    private final Destination destination;

    @OneToMany
    @JoinColumn(name = "TRANSFER_ID")
    private final Set<TransferStop> stops;

    TransferPath(Destination destination, Set<TransferStop> stops) {
        this.destination = destination;
        this.stops = stops;
    }

    TransferPath(Destination destination) {
        this.destination = destination;
        this.stops = new HashSet<>();
    }

    Destination destination() {
        return destination;
    }

    Set<TransferStop> stops() {
        return stops;
    }
}
