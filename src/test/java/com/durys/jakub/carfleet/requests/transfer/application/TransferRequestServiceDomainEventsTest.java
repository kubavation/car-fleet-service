package com.durys.jakub.carfleet.requests.transfer.application;

import com.durys.jakub.carfleet.cars.domain.Car;
import com.durys.jakub.carfleet.cars.domain.CarFactory;
import com.durys.jakub.carfleet.cars.domain.CarId;
import com.durys.jakub.carfleet.cars.domain.CarsRepository;
import com.durys.jakub.carfleet.cars.domain.basicinformation.FuelType;
import com.durys.jakub.carfleet.cars.infrastructure.MockedCarsRepository;
import com.durys.jakub.carfleet.common.errors.ValidationErrorHandlers;
import com.durys.jakub.carfleet.events.Events;
import com.durys.jakub.carfleet.sharedkernel.requests.RequestId;
import com.durys.jakub.carfleet.sharedkernel.requests.RequesterId;
import com.durys.jakub.carfleet.requests.transfer.domain.TransferRequest;
import com.durys.jakub.carfleet.requests.transfer.domain.state.commands.AssignTransferCarCommand;
import com.durys.jakub.carfleet.sharedkernel.cars.CarType;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.UUID;

@SpringBootTest
class TransferRequestServiceDomainEventsTest {

    private final CarsRepository carsRepository = new MockedCarsRepository();

    @MockBean
    private Events events;

    @InjectMocks
    @Autowired
    private TransferRequestService transferRequestService;


    @Test
    void shouldSendRequestAcceptedDomainEvent() {
        RequestId requestId = addTransferRequest();
        CarId carId = addCar(CarType.Passenger);

        transferRequestService.changeStatus(requestId, new AssignTransferCarCommand(carId));

        Mockito.verify(events, Mockito.times(1)).publish(Mockito.any());
    }

    public RequestId addTransferRequest() {
        var transferRequest = transferRequestService
                .create(new RequesterId(UUID.randomUUID()), LocalDateTime.now(), LocalDateTime.now().plusDays(1),
                "test", "Warsaw",  "Krakow", CarType.Passenger).get();
        return transferRequest.requestId();
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
