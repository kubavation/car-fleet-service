package com.durys.jakub.carfleet.requests.application;

import com.durys.jakub.carfleet.drivers.DriverId;
import com.durys.jakub.carfleet.requests.drivertransfer.DriverTransferRequest;
import com.durys.jakub.carfleet.requests.drivertransfer.DriverTransferRequestAssembler;
import com.durys.jakub.carfleet.requests.RequestId;
import com.durys.jakub.carfleet.requests.RequesterId;
import com.durys.jakub.carfleet.requests.drivertransfer.vo.RequestPurpose;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class RequestServiceTest {

    private final RequestService requestService = new RequestService(new DriverTransferRequestAssembler());


    private RequesterId requesterId = new RequesterId(UUID.randomUUID());
    private LocalDateTime from = LocalDateTime.now();
    private LocalDateTime to = LocalDateTime.now();
    private RequestPurpose purpose = new RequestPurpose("Content");

    @Test
    void shouldCreateRequestAndChangeStatusToNew() {

        DriverTransferRequest driverTransferRequest = requestService.create(requesterId, from, to, purpose);

        assertEquals("NEW", driverTransferRequest.state());
    }

    @Test
    void shouldChangeRequestContentAndChangeStatusToEdited() {

        RequestId requestId = new RequestId(UUID.randomUUID());

        DriverTransferRequest driverTransferRequest = requestService.change(requestId, from, to, purpose);

        assertEquals("EDITED", driverTransferRequest.state());
    }


    @Test
    void shouldSaveRequestWithDriverAndChangeStatusToAccepted() {

        RequestId requestId = new RequestId(UUID.randomUUID());
        DriverId driverId = new DriverId(UUID.randomUUID());

        DriverTransferRequest driverTransferRequest = requestService.changeStatus(requestId, driverId);

        assertEquals("ACCEPTED", driverTransferRequest.state());
        assertEquals(driverId, driverTransferRequest.getDriverId());
    }


}