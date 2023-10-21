package com.durys.jakub.carfleet.requests.drivertransfer.domain;

import com.durys.jakub.carfleet.requests.RequestId;

import java.util.Optional;

public interface DriverTransferRequestRepository {
    Optional<DriverTransferRequest> load(RequestId requestId);
    DriverTransferRequest save(DriverTransferRequest request);
}
