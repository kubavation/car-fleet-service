package com.durys.jakub.carfleet.transfers.domain;

public interface TransferRepository {
    Transfer load(TransferId id);
    Transfer save(Transfer transfer);
}
