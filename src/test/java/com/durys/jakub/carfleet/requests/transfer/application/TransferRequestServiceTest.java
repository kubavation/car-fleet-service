package com.durys.jakub.carfleet.requests.transfer.application;

import com.durys.jakub.carfleet.cars.availability.CarAvailabilityService;
import com.durys.jakub.carfleet.cars.availability.DefaultCarAvailabilityService;
import com.durys.jakub.carfleet.requests.RequesterId;
import com.durys.jakub.carfleet.requests.transfer.domain.TransferRequest;
import com.durys.jakub.carfleet.requests.transfer.domain.TransferRequestAssembler;
import com.durys.jakub.carfleet.requests.transfer.domain.TransferRequestRepository;
import com.durys.jakub.carfleet.requests.transfer.instrastructure.MockedTransferRequestRepository;
import com.durys.jakub.carfleet.requests.vo.RequestPurpose;
import org.bouncycastle.cert.ocsp.Req;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class TransferRequestServiceTest {

    private final CarAvailabilityService carAvailabilityService = Mockito.mock(DefaultCarAvailabilityService.class);
    private final TransferRequestRepository transferRequestRepository = new MockedTransferRequestRepository();
    private final TransferRequestAssembler assembler = new TransferRequestAssembler(carAvailabilityService);

    private final TransferRequestService transferRequestService = new TransferRequestService(assembler, transferRequestRepository);


    @Test
    void shouldCreateTransferRequest() {

        RequesterId requesterId = new RequesterId(UUID.randomUUID());
        LocalDateTime from = LocalDateTime.now();
        LocalDateTime to = LocalDateTime.now().plusDays(1);
        RequestPurpose purpose = new RequestPurpose("test");
        String departure = "Warsaw";
        String destination = "Krakow";

        TransferRequest transferRequest = transferRequestService.create(requesterId, from, to, purpose, departure, destination);
        assertNotNull(transferRequest);
    }

}