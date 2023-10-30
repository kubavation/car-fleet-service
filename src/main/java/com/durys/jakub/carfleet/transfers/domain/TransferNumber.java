package com.durys.jakub.carfleet.transfers.domain;

class TransferNumber {

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
