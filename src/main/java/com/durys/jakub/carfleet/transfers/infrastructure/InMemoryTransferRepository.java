package com.durys.jakub.carfleet.transfers.infrastructure;

import com.durys.jakub.carfleet.transfers.domain.Transfer;
import com.durys.jakub.carfleet.transfers.domain.TransferId;
import com.durys.jakub.carfleet.transfers.domain.TransferRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InMemoryTransferRepository implements TransferRepository {

    private static final Map<TransferId, Transfer> DB = new HashMap<>();

    @Override
    public Transfer load(TransferId transferId) {
        var transfer = DB.get(transferId);
        if (transfer == null) {
            throw new RuntimeException("Transfer not found");
        }
        return transfer;
    }

    @Override
    public Transfer save(Transfer transfer) {
        DB.put(transfer.transferId(), transfer);
        return load(transfer.transferId());
    }
}
