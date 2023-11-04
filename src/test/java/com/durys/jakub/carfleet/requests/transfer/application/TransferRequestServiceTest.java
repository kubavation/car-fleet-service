package com.durys.jakub.carfleet.requests.transfer.application;

import com.durys.jakub.carfleet.cars.availability.CarAvailabilityService;
import com.durys.jakub.carfleet.cars.domain.Car;
import com.durys.jakub.carfleet.cars.domain.CarFactory;
import com.durys.jakub.carfleet.cars.domain.CarId;
import com.durys.jakub.carfleet.cars.domain.CarsRepository;
import com.durys.jakub.carfleet.cars.domain.basicinformation.FuelType;
import com.durys.jakub.carfleet.cars.infrastructure.MockedCarsRepository;
import com.durys.jakub.carfleet.common.errors.ValidationError;
import com.durys.jakub.carfleet.common.errors.ValidationErrorHandlers;
import com.durys.jakub.carfleet.events.Events;
import com.durys.jakub.carfleet.requests.RequestId;
import com.durys.jakub.carfleet.requests.RequesterId;
import com.durys.jakub.carfleet.requests.transfer.domain.TransferRequest;
import com.durys.jakub.carfleet.requests.transfer.domain.TransferRequestAssembler;
import com.durys.jakub.carfleet.requests.transfer.domain.TransferRequestRepository;
import com.durys.jakub.carfleet.requests.transfer.domain.TransferRequestStatus;
import com.durys.jakub.carfleet.requests.transfer.domain.state.commands.AssignTransferCarCommand;
import com.durys.jakub.carfleet.requests.transfer.instrastructure.InMemoryTransferRequestRepository;
import com.durys.jakub.carfleet.sharedkernel.cars.CarType;
import com.durys.jakub.carfleet.state.ChangeCommand;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class TransferRequestServiceTest {

    private final CarAvailabilityService carAvailabilityService = Mockito.mock();
    private final TransferRequestRepository transferRequestRepository = new InMemoryTransferRequestRepository();
    private final CarsRepository carsRepository = new MockedCarsRepository();
    private final TransferRequestAssembler assembler = new TransferRequestAssembler(carAvailabilityService, carsRepository);

    private final Events events = Mockito.mock();

    private final TransferRequestService transferRequestService
            = new TransferRequestService(assembler, transferRequestRepository, events);


    @Test
    void shouldCreateTransferRequest() {

        RequesterId requesterId = new RequesterId(UUID.randomUUID());
        LocalDateTime from = LocalDateTime.now();
        LocalDateTime to = LocalDateTime.now().plusDays(1);
        String purpose = "test";
        String departure = "Warsaw";
        String destination = "Krakow";
        CarType carType = CarType.Passenger;

        TransferRequest transferRequest = transferRequestService
                .create(requesterId, from, to, purpose, departure, destination, carType);

        assertNotNull(transferRequest);
    }

    @Test
    void shouldAcceptTransferRequest() {

        RequestId requestId = addTransferRequest();
        CarId carId = addCar(CarType.Passenger);

        Mockito.when(carAvailabilityService.available(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(true);

        TransferRequest transferRequest = transferRequestService.changeStatus(requestId, new AssignTransferCarCommand(carId));

        assertEquals("ACCEPTED", transferRequest.state());
        assertEquals(carId, transferRequest.assignedCar());
        Mockito.verify(events, Mockito.times(1)).publish(Mockito.any());
    }

    @Test
    void shouldNotAcceptTransferRequest_whenAssignedCarIsNotOfRequestedType() {

        RequestId requestId = addTransferRequest();
        CarId carId = addCar(CarType.Bus);

        Mockito.when(carAvailabilityService.available(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(true);

        ValidationError validationException
                = assertThrows(ValidationError.class, () -> transferRequestService.changeStatus(requestId, new AssignTransferCarCommand(carId)));

        assertEquals("Invalid assigned car type", validationException.getMessage());
    }

    @Test
    void shouldRejectTransferRequest() {

        RequestId requestId = addTransferRequest();

        TransferRequest transferRequest = transferRequestService.changeStatus(requestId, new ChangeCommand(TransferRequestStatus.REJECTED));

        assertEquals("REJECTED", transferRequest.state());
    }


    public RequestId addTransferRequest() {
        TransferRequest transferRequest = transferRequestService
                .create(new RequesterId(UUID.randomUUID()), LocalDateTime.now(), LocalDateTime.now().plusDays(1),
                        "test", "Warsaw",  "Krakow", CarType.Passenger);
        return transferRequest.getRequestId();
    }

    private CarId addCar(CarType carType) {

        CarId carId = new CarId(UUID.randomUUID());

        Car registeredCar = CarFactory
                .withValidationErrorHandler(ValidationErrorHandlers.aggregatingValidationErrorHandler())
                .with(carId, carType)
                .withBasicInformation("123", "123", FuelType.GASOLINE)
                .construct();

        carsRepository.save(registeredCar);
        return carId;
    }

}