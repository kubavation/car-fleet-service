package com.durys.jakub.carfleet.requests.application;

import com.durys.jakub.carfleet.drivers.DriverId;
import com.durys.jakub.carfleet.requests.drivertransfer.application.DriverTransferRequestService;
import com.durys.jakub.carfleet.requests.drivertransfer.domain.DriverTransferRequest;
import com.durys.jakub.carfleet.requests.drivertransfer.domain.DriverTransferRequestAssembler;
import com.durys.jakub.carfleet.requests.RequestId;
import com.durys.jakub.carfleet.requests.RequesterId;
import com.durys.jakub.carfleet.requests.drivertransfer.domain.commands.ChangeDriverCommand;
import com.durys.jakub.carfleet.requests.vo.RequestPurpose;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class DriverTransferRequestServiceTest {

    private final DriverTransferRequestService driverTransferRequestService = new DriverTransferRequestService(new DriverTransferRequestAssembler());


    private final RequesterId requesterId = new RequesterId(UUID.randomUUID());
    private final LocalDateTime from = LocalDateTime.now();
    private final LocalDateTime to = LocalDateTime.now();
    private final RequestPurpose purpose = new RequestPurpose("Content");

    @Test
    void shouldCreateRequestAndChangeStatusToNew() {

        DriverTransferRequest driverTransferRequest = driverTransferRequestService.create(requesterId, from, to, purpose);

        assertEquals("NEW", driverTransferRequest.state());
    }

    @Test
    void shouldChangeRequestContentAndChangeStatusToEdited() {

        RequestId requestId = new RequestId(UUID.randomUUID());

        DriverTransferRequest driverTransferRequest = driverTransferRequestService.change(requestId, from, to, purpose);

        assertEquals("EDITED", driverTransferRequest.state());
    }

    @Test
    void shouldSaveRequestWithDriverAndChangeStatusToAccepted() {

        RequestId requestId = new RequestId(UUID.randomUUID());
        DriverId driverId = new DriverId(UUID.randomUUID());

        DriverTransferRequest driverTransferRequest = driverTransferRequestService.changeStatus(requestId, new ChangeDriverCommand(driverId));

        assertEquals("ACCEPTED", driverTransferRequest.state());
        assertEquals(driverId, driverTransferRequest.getDriverId());
    }


}