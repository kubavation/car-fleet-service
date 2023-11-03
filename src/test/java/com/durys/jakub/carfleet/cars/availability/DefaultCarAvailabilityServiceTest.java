package com.durys.jakub.carfleet.cars.availability;

import com.durys.jakub.carfleet.cars.domain.*;
import com.durys.jakub.carfleet.cars.domain.basicinformation.FuelType;
import com.durys.jakub.carfleet.cars.domain.tenchicalinspection.TechnicalInspection;
import com.durys.jakub.carfleet.cars.infrastructure.MockedCarsRepository;
import com.durys.jakub.carfleet.common.errors.ValidationErrorHandlers;
import com.durys.jakub.carfleet.sharedkernel.cars.CarType;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DefaultCarAvailabilityServiceTest {

    private final CarsRepository carsRepository = new MockedCarsRepository();

    private final CarAvailabilityService carAvailabilityService
            = new DefaultCarAvailabilityService(null, carsRepository);


    private final CarId carId = new CarId(UUID.randomUUID());

    private final Car car = createCar(carId);

    @Test
    void shouldReturnCarAvailable() {

        carsRepository.save(car);

        boolean available = carAvailabilityService.available(carId, LocalDateTime.now(), LocalDateTime.now().plusDays(3));

        assertTrue(available);
    }

    @Test
    void whenCarInspectionWhileTransfer_shouldReturnCarNotAvailable() {

        LocalDateTime transferFrom = LocalDate.of(2023, 1, 1).atTime(8, 0);
        LocalDateTime transferTo = LocalDate.of(2023, 1, 3).atTime(16, 0);

        addTechnicalInspection(car,  LocalDate.of(2023, 1, 3));

        carsRepository.save(car);


        boolean available = carAvailabilityService.available(carId, transferFrom, transferTo);

        assertFalse(available);
    }

    @Test
    void whenCarInspectionAfterTransfer_shouldReturnCarAvailable() {

        LocalDateTime transferFrom = LocalDate.of(2023, 1, 1).atTime(8, 0);
        LocalDateTime transferTo = LocalDate.of(2023, 1, 3).atTime(16, 0);

        addTechnicalInspection(car, LocalDate.of(2023, 1, 4));

        carsRepository.save(car);


        boolean available = carAvailabilityService.available(carId, transferFrom, transferTo);

        assertTrue(available);
    }


    private static void addTechnicalInspection(Car car, LocalDate nextInspectionAt) {

        TechnicalInspection inspection = new TechnicalInspection(
                LocalDate.of(2022, 1, 3), "Description", BigDecimal.valueOf(2000), nextInspectionAt);

        car.undergoTechnicalInspection(inspection);
    }

    private static Car createCar(CarId carId) {
        return CarFactory.withValidationErrorHandler(ValidationErrorHandlers.throwingValidationErrorHandler())
                .with(carId, CarType.Passenger)
                .withBasicInformation("123", "123", FuelType.GASOLINE)
                .construct();
    }

}