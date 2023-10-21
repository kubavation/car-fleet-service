package com.durys.jakub.carfleet.requests.drivertransfer.domain;

import com.durys.jakub.carfleet.requests.RequesterId;

import java.util.Optional;

public interface DriverTransferRequestRepository {
    Optional<DriverTransferRequest> load(RequesterId requesterId);
    void save(DriverTransferRequest request);
}
