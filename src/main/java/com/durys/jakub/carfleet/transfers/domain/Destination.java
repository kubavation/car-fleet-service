package com.durys.jakub.carfleet.transfers.domain;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
class Destination {

    private final String name;

    Destination(String name) {
        this.name = name;
    }

    String name() {
        return name;
    }
}
