package com.durys.jakub.carfleet.requests.transfer.instrastructure;

import com.durys.jakub.carfleet.requests.RequestId;
import com.durys.jakub.carfleet.requests.transfer.domain.TransferRequest;
import com.durys.jakub.carfleet.requests.transfer.domain.TransferRequestRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InMemoryTransferRequestRepository implements TransferRequestRepository {

    private static final Map<RequestId, TransferRequest> DB = new HashMap<>();

    @Override
    public Optional<TransferRequest> load(RequestId requestId) {
        return Optional.ofNullable(DB.get(requestId));
    }

    @Override
    public TransferRequest save(TransferRequest request) {
        DB.put(request.getRequestId(), request);
        return load(request.getRequestId()).get();
    }
}
