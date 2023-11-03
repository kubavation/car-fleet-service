package com.durys.jakub.carfleet.requests.transfer.application;

import com.durys.jakub.carfleet.cars.availability.CarAvailabilityService;
import com.durys.jakub.carfleet.cars.availability.DefaultCarAvailabilityService;
import com.durys.jakub.carfleet.cars.domain.Car;
import com.durys.jakub.carfleet.cars.domain.CarFactory;
import com.durys.jakub.carfleet.cars.domain.CarId;
import com.durys.jakub.carfleet.cars.domain.CarsRepository;
import com.durys.jakub.carfleet.cars.domain.basicinformation.FuelType;
import com.durys.jakub.carfleet.cars.infrastructure.MockedCarsRepository;
import com.durys.jakub.carfleet.common.errors.ValidationErrorHandlers;
import com.durys.jakub.carfleet.requests.RequestId;
import com.durys.jakub.carfleet.requests.RequesterId;
import com.durys.jakub.carfleet.requests.transfer.domain.TransferRequest;
import com.durys.jakub.carfleet.requests.transfer.domain.TransferRequestAssembler;
import com.durys.jakub.carfleet.requests.transfer.domain.TransferRequestRepository;
import com.durys.jakub.carfleet.requests.transfer.domain.state.commands.AssignTransferCarCommand;
import com.durys.jakub.carfleet.requests.transfer.instrastructure.InMemoryTransferRequestRepository;
import com.durys.jakub.carfleet.sharedkernel.cars.CarType;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class TransferRequestServiceTest {

    private final CarAvailabilityService carAvailabilityService = Mockito.mock(DefaultCarAvailabilityService.class);
    private final TransferRequestRepository transferRequestRepository = new InMemoryTransferRequestRepository();
    private final CarsRepository carsRepository = new MockedCarsRepository();
    private final TransferRequestAssembler assembler = new TransferRequestAssembler(carAvailabilityService, carsRepository);

    private final TransferRequestService transferRequestService = new TransferRequestService(assembler, transferRequestRepository);


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
    }


    public RequestId addTransferRequest() {
        RequesterId requesterId = new RequesterId(UUID.randomUUID());
        LocalDateTime from = LocalDateTime.now();
        LocalDateTime to = LocalDateTime.now().plusDays(1);
        String purpose = "test";
        String departure = "Warsaw";
        String destination = "Krakow";
        CarType carType = CarType.Passenger;

        TransferRequest transferRequest = transferRequestService
                .create(requesterId, from, to, purpose, departure, destination, carType);
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