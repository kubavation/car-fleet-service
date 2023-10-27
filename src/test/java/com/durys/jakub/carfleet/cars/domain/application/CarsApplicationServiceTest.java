package com.durys.jakub.carfleet.cars.domain.application;

import com.durys.jakub.carfleet.cars.domain.CarType;
import com.durys.jakub.carfleet.cars.domain.CarsRepository;
import com.durys.jakub.carfleet.cars.domain.basicinformation.FuelType;
import com.durys.jakub.carfleet.cars.infrastructure.MockedCarsRepository;
import com.durys.jakub.carfleet.common.OperationResult;
import org.junit.jupiter.api.Test;

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

}