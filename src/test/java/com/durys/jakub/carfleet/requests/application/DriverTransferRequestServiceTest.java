package com.durys.jakub.carfleet.requests.application;

import com.durys.jakub.carfleet.cars.availability.CarAvailabilityService;
import com.durys.jakub.carfleet.cars.availability.DefaultCarAvailabilityService;
import com.durys.jakub.carfleet.cars.domain.*;
import com.durys.jakub.carfleet.cars.domain.basicinformation.FuelType;
import com.durys.jakub.carfleet.cars.infrastructure.MockedCarsRepository;
import com.durys.jakub.carfleet.common.errors.ValidationErrorHandlers;
import com.durys.jakub.carfleet.drivers.domain.DriverId;
import com.durys.jakub.carfleet.requests.drivertransfer.application.DriverTransferRequestService;
import com.durys.jakub.carfleet.requests.drivertransfer.domain.DriverTransferRequest;
import com.durys.jakub.carfleet.requests.drivertransfer.domain.DriverTransferRequestAssembler;
import com.durys.jakub.carfleet.requests.RequestId;
import com.durys.jakub.carfleet.requests.RequesterId;
import com.durys.jakub.carfleet.requests.drivertransfer.domain.DriverTransferRequestStatus;
import com.durys.jakub.carfleet.requests.drivertransfer.domain.commands.ChangeTransportInformationCommand;
import com.durys.jakub.carfleet.requests.drivertransfer.infrastructure.MockedDriverTransferRequestRepository;
import com.durys.jakub.carfleet.state.ChangeCommand;
import com.durys.jakub.carfleet.requests.vo.RequestPurpose;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class DriverTransferRequestServiceTest {

    private final CarsRepository carsRepository = new MockedCarsRepository();

    @Mock
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final CarAvailabilityService carAvailabilityService = new DefaultCarAvailabilityService(namedParameterJdbcTemplate, carsRepository);

    private final DriverTransferRequestService driverTransferRequestService
            = new DriverTransferRequestService(new DriverTransferRequestAssembler(carAvailabilityService), new MockedDriverTransferRequestRepository());


    private final RequesterId requesterId = new RequesterId(UUID.randomUUID());
    private final LocalDateTime from = LocalDateTime.now();
    private final LocalDateTime to = LocalDateTime.now();
    private final RequestPurpose purpose = new RequestPurpose("Content");
    private final String departure = "Warsaw";
    private final String destination = "Krakow";

    @Test
    void shouldCreateRequestAndChangeStatusToNew() {

        DriverTransferRequest driverTransferRequest = driverTransferRequestService
                .create(requesterId, from, to, purpose, departure, destination);

        assertEquals("NEW", driverTransferRequest.state());
    }

    @Test
    void shouldChangeRequestContentAndChangeStatusToEdited() {

        DriverTransferRequest result = driverTransferRequestService
                .create(requesterId, from, to, purpose, departure, destination);

        DriverTransferRequest driverTransferRequest = driverTransferRequestService.change(result.getRequestId(),
                from, to, purpose, departure, destination);

        assertEquals("EDITED", driverTransferRequest.state());
    }

    @Test
    void shouldSaveRequestWithDriverAndChangeStatusToAccepted() {

        RequestId requestId = new RequestId(UUID.randomUUID());
        DriverId driverId = new DriverId(UUID.randomUUID());
        CarId carId = addCar();

        DriverTransferRequest result = driverTransferRequestService
                .create(requesterId, from, to, purpose, departure, destination);

        DriverTransferRequest driverTransferRequest = driverTransferRequestService.changeStatus(result.getRequestId(),
                new ChangeTransportInformationCommand(driverId, carId));

        assertEquals("ACCEPTED", driverTransferRequest.state());
        assertEquals(driverId, driverTransferRequest.getDriverId());
        assertEquals(carId, driverTransferRequest.getCarId());
    }

    @Test
    void shouldSaveRequestAndChangeStatusToRejected() {

        DriverTransferRequest result = driverTransferRequestService
                .create(requesterId, from, to, purpose, departure, destination);

        DriverTransferRequest driverTransferRequest = driverTransferRequestService.changeStatus(result.getRequestId(),
                new ChangeCommand(DriverTransferRequestStatus.REJECTED));

        assertEquals("REJECTED", driverTransferRequest.state());
    }

    @Test
    void shouldSaveRequestAndChangeStatusToCanceled() {

        DriverId driverId = new DriverId(UUID.randomUUID());
        CarId carId = addCar();


        DriverTransferRequest result = driverTransferRequestService
                .create(requesterId, from, to, purpose, departure, destination);

        DriverTransferRequest saved = driverTransferRequestService.changeStatus(result.getRequestId(),
                new ChangeTransportInformationCommand(driverId, carId));


        DriverTransferRequest driverTransferRequest = driverTransferRequestService.changeStatus(saved.getRequestId(),
                new ChangeCommand(DriverTransferRequestStatus.CANCELLED));

        assertEquals("CANCELLED", driverTransferRequest.state());
    }


    private CarId addCar() {

        CarId carId = new CarId(UUID.randomUUID());

        Car registeredCar = CarFactory
                .withValidationErrorHandler(ValidationErrorHandlers.aggregatingValidationErrorHandler())
                .with(carId, CarType.Passenger)
                .withBasicInformation("123", "123", FuelType.GASOLINE)
                .construct();

        carsRepository.save(registeredCar);
        return carId;
    }

}