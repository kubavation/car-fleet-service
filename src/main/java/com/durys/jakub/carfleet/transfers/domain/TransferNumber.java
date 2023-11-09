package com.durys.jakub.carfleet.transfers.domain;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
class TransferNumber implements Serializable {

    private final String value;

    TransferNumber(TransferPath transferPath, Transfer.Type transferType, TransferPeriod period) {
        this("%s-%s_%s".formatted(transferPath.to(), transferType.name(), period.from().toLocalDate()));
    }

    TransferNumber(String value) {
        this.value = value;
    }

    String value() {
        return value;
    }
}
