package com.durys.jakub.carfleet.requests.transfer.domain;

import com.durys.jakub.carfleet.requests.RequestId;

public interface TransferRequestRepository {
    TransferRequest load(RequestId requestId);
    void save(TransferRequest request);
}
