package com.durys.jakub.carfleet.cars.domain.application;

import com.durys.jakub.carfleet.cars.domain.*;
import com.durys.jakub.carfleet.cars.domain.basicinformation.FuelType;
import com.durys.jakub.carfleet.cars.infrastructure.MockedCarsRepository;
import com.durys.jakub.carfleet.common.OperationResult;
import com.durys.jakub.carfleet.common.errors.ValidationErrorHandlers;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class CarsApplicationServiceTest {

    private final CarsRepository carsRepository = new MockedCarsRepository();
    private final CarsApplicationService carsApplicationService = new CarsApplicationService(carsRepository);


    @Test
    void shouldSuccessfullyCreateCarAndReturnSuccessfulOperationResult() {

        CarType carType = CarType.Passenger;
        String registrationNumber = "123";
        String vin = "123";
        FuelType fuelType = FuelType.GASOLINE;

        OperationResult result = carsApplicationService.register(carType, registrationNumber, vin, fuelType);

        assertEquals(OperationResult.Status.Success, result.status());
    }



    @Test
    void shouldSuccessfullyCreateCarAndReturnFailureResultWhenRegistrationNumberIsEmpty() {

        CarType carType = CarType.Passenger;
        String registrationNumber = null;
        String vin = "123";
        FuelType fuelType = FuelType.GASOLINE;

        OperationResult result = carsApplicationService.register(carType, registrationNumber, vin, fuelType);

        assertEquals(OperationResult.Status.Failure, result.status());
        assertFalse(result.errorMessages().isEmpty());
        assertEquals("Registration number cannot be empty", result.errorMessages().iterator().next());
    }


    @Test
    void shouldSuccessfullyUnregisterCar() {

        CarId carId = addCar();

        OperationResult result = carsApplicationService.unregister(carId);

        assertEquals(OperationResult.Status.Success, result.status());
        assertEquals(Car.CarStatus.Unregistered, find(carId).status());
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

    private Car find(CarId carId) {
        return carsRepository.load(carId)
                .orElseThrow(() -> new RuntimeException("TESTS | Car not found"));
    }

}