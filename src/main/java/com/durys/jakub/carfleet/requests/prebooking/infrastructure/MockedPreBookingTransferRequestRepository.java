package com.durys.jakub.carfleet.requests.prebooking.infrastructure;

import com.durys.jakub.carfleet.requests.RequestId;
import com.durys.jakub.carfleet.requests.prebooking.domain.PreBookingTransferRequest;
import com.durys.jakub.carfleet.requests.prebooking.domain.PreBookingTransferRequestRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class MockedPreBookingTransferRequestRepository implements PreBookingTransferRequestRepository {

    private static final Map<RequestId, PreBookingTransferRequest> DB = new HashMap<>();

    @Override
    public Optional<PreBookingTransferRequest> load(RequestId requestId) {
        return Optional.ofNullable(DB.get(requestId));
    }

    @Override
    public PreBookingTransferRequest save(PreBookingTransferRequest request) {
        DB.put(request.getRequestId(), request);
        return load(request.getRequestId()).get();
    }
}
