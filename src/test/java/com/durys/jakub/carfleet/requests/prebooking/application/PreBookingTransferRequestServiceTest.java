package com.durys.jakub.carfleet.requests.prebooking.application;

import com.durys.jakub.carfleet.cars.domain.CarId;
import com.durys.jakub.carfleet.drivers.domain.DriverId;
import com.durys.jakub.carfleet.events.Events;
import com.durys.jakub.carfleet.sharedkernel.requests.RequesterId;
import com.durys.jakub.carfleet.requests.prebooking.domain.PreBookingTransferRequest;
import com.durys.jakub.carfleet.requests.prebooking.domain.PreBookingTransferRequestAssembler;
import com.durys.jakub.carfleet.requests.prebooking.domain.commands.RealizePreBookingRequestCommand;
import com.durys.jakub.carfleet.requests.prebooking.infrastructure.MockedPreBookingTransferRequestRepository;
import com.durys.jakub.carfleet.requests.vo.RequestPurpose;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class PreBookingTransferRequestServiceTest {

    private final Events events = mock(Events.class);

    private final PreBookingTransferRequestService preBookingTransferRequestService = new PreBookingTransferRequestService(
            new PreBookingTransferRequestAssembler(events), new MockedPreBookingTransferRequestRepository(), events);

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

    @Test
    void shouldChangePreBookingTransferRequestStatusToArchived() {

        PreBookingTransferRequest request = preBookingTransferRequestService
                .create(requesterId, from, to, purpose, carId, driverId);

        PreBookingTransferRequest result = preBookingTransferRequestService
                .changeStatus(request.getRequestId(), new RealizePreBookingRequestCommand(request.getRequesterId()));

        assertEquals("ARCHIVED", result.state());
        verify(events).publish(any());
    }

}