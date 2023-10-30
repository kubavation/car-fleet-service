package com.durys.jakub.carfleet.requests.transfer.domain;

import com.durys.jakub.carfleet.requests.RequestId;

import java.util.Optional;

public interface TransferRequestRepository {
    Optional<TransferRequest> load(RequestId requestId);
    TransferRequest save(TransferRequest request);
}
