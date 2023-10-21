package com.durys.jakub.carfleet.requests.application;

import com.durys.jakub.carfleet.drivers.DriverId;
import com.durys.jakub.carfleet.requests.Request;
import com.durys.jakub.carfleet.requests.RequestAssembler;
import com.durys.jakub.carfleet.requests.RequestId;
import com.durys.jakub.carfleet.requests.RequesterId;
import com.durys.jakub.carfleet.requests.vo.RequestPurpose;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class RequestServiceTest {

    private final RequestService requestService = new RequestService(new RequestAssembler());


    private RequesterId requesterId = new RequesterId(UUID.randomUUID());
    private LocalDateTime from = LocalDateTime.now();
    private LocalDateTime to = LocalDateTime.now();
    private RequestPurpose purpose = new RequestPurpose("Content");

    @Test
    void shouldCreateRequestAndChangeStatusToNew() {

        Request request = requestService.create(requesterId, from, to, purpose);

        assertEquals("NEW", request.getState());
    }

    @Test
    void shouldChangeRequestContentAndChangeStatusToEdited() {

        RequestId requestId = new RequestId(UUID.randomUUID());

        Request request = requestService.change(requestId, from, to, purpose);

        assertEquals("EDITED", request.getState());
    }


    @Test
    void shouldSaveRequestWithDriverAndChangeStatusToAccepted() {

        RequestId requestId = new RequestId(UUID.randomUUID());
        DriverId driverId = new DriverId(UUID.randomUUID());

        Request request = requestService.changeStatus(requestId, driverId);

        assertEquals("ACCEPTED", request.getState());
        assertEquals(driverId, request.getDriverId());
    }


}