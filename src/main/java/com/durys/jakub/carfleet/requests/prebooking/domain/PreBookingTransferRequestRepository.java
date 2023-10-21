package com.durys.jakub.carfleet.requests.prebooking.domain;

import com.durys.jakub.carfleet.requests.RequestId;

import java.util.Optional;

public interface PreBookingTransferRequestRepository {
    Optional<PreBookingTransferRequest> load(RequestId requestId);
    PreBookingTransferRequest save(PreBookingTransferRequest request);
}
