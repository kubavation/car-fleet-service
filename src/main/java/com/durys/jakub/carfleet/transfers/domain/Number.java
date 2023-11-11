package com.durys.jakub.carfleet.transfers.domain;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
class Number implements Serializable {

    private final String value;

    Number(Destination destination, Transfer.Type transferType, TransferPeriod period) {
        this("%s-%s_%s".formatted(destination.name(), transferType.name(), period.from().toLocalDate()));
    }

    Number(String value) {
        this.value = value;
    }

    String value() {
        return value;
    }
}
