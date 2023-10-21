package com.durys.jakub.carfleet.requests.prebooking.application;

import com.durys.jakub.carfleet.cars.domain.CarId;
import com.durys.jakub.carfleet.drivers.DriverId;
import com.durys.jakub.carfleet.requests.RequestId;
import com.durys.jakub.carfleet.requests.RequesterId;
import com.durys.jakub.carfleet.requests.prebooking.domain.PreBookingTransferRequest;
import com.durys.jakub.carfleet.requests.prebooking.domain.PreBookingTransferRequestAssembler;
import com.durys.jakub.carfleet.requests.prebooking.infrastructure.MockedPreBookingTransferRequestRepository;
import com.durys.jakub.carfleet.requests.vo.RequestPurpose;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class PreBookingTransferRequestServiceTest {

    private final PreBookingTransferRequestService preBookingTransferRequestService = new PreBookingTransferRequestService(
            new PreBookingTransferRequestAssembler(), new MockedPreBookingTransferRequestRepository());

    private final RequesterId requesterId = new RequesterId(UUID.randomUUID());
    private final LocalDateTime from = LocalDateTime.now();
    private final LocalDateTime to = LocalDateTime.now();
    private final RequestPurpose purpose = new RequestPurpose("Content");
    private final CarId carId = new CarId(UUID.randomUUID());
    private final DriverId driverId = new DriverId(UUID.randomUUID());

    @Test
    void shouldSavePreBookingTransferRequestAndSetNewStatus() {


        PreBookingTransferRequest request = preBookingTransferRequestService
                .create(requesterId, from, to, purpose, carId, driverId);

        assertEquals("NEW", request.state());
    }

}