package com.durys.jakub.carfleet.transfers.domain;

import java.time.LocalDateTime;

class TransferNumber {

    private final String value;

    TransferNumber(TransferPath transferPath, LocalDateTime at) {
        this("%s-%s_%s".formatted(transferPath.from().name(), transferPath.to().name(), at.toLocalDate()));
    }

    TransferNumber(String value) {
        this.value = value;
    }

    String value() {
        return value;
    }
}
