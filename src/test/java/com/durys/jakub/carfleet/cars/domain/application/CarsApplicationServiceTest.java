package com.durys.jakub.carfleet.cars.domain.application;

import com.durys.jakub.carfleet.cars.domain.*;
import com.durys.jakub.carfleet.cars.domain.basicinformation.FuelType;
import com.durys.jakub.carfleet.cars.domain.tenchicalinspection.Mileage;
import com.durys.jakub.carfleet.cars.infrastructure.MockedCarsRepository;
import com.durys.jakub.carfleet.common.OperationResult;
import com.durys.jakub.carfleet.common.errors.ValidationErrorHandlers;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
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

    @Test
    void shouldSuccessfullyUndergoTechnicalInspection() {

        CarId carId = addCar();

        LocalDate at = LocalDate.now();
        String description = "technical inspection";
        BigDecimal mileage = BigDecimal.TEN;
        LocalDate nextAt = LocalDate.of(2024, 1 , 1);

        OperationResult result = carsApplicationService.undergoTechnicalInspection(carId, at, description, mileage, nextAt);

        assertEquals(OperationResult.Status.Success, result.status());
        assertEquals(nextAt, find(carId).nextTechnicalInspectionAt());
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