package com.durys.jakub.carfleet.requests.transfer.domain;

import com.durys.jakub.carfleet.ddd.Repository;
import com.durys.jakub.carfleet.sharedkernel.requests.RequestId;

import java.util.Optional;

public interface TransferRequestRepository extends Repository<TransferRequest> {
    Optional<TransferRequest> load(RequestId requestId);
    TransferRequest save(TransferRequest request);
}
