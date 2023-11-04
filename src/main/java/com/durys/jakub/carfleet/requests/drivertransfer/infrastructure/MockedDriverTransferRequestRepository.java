package com.durys.jakub.carfleet.requests.drivertransfer.infrastructure;

import com.durys.jakub.carfleet.sharedkernel.requests.RequestId;
import com.durys.jakub.carfleet.requests.drivertransfer.domain.DriverTransferRequest;
import com.durys.jakub.carfleet.requests.drivertransfer.domain.DriverTransferRequestRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class MockedDriverTransferRequestRepository implements DriverTransferRequestRepository {

    private static final Map<RequestId, DriverTransferRequest> DB = new HashMap<>();

    @Override
    public Optional<DriverTransferRequest> load(RequestId requestId) {
        return Optional.ofNullable(DB.get(requestId));
    }

    @Override
    public DriverTransferRequest save(DriverTransferRequest request) {
        DB.put(request.getRequestId(), request);
        return load(request.getRequestId()).get();
    }
}
