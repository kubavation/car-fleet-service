package com.durys.jakub.carfleet.transfers.domain;

import java.time.LocalDateTime;

class TransferNumber {

    private final String value;

    TransferNumber(TransferPath transferPath, Transfer.Type transferType, LocalDateTime at) {
        this("%s-%s_%s".formatted(transferPath.to(), transferType.name(), at.toLocalDate()));
    }

    TransferNumber(String value) {
        this.value = value;
    }

    String value() {
        return value;
    }
}
