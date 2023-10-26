package com.durys.jakub.carfleet.cars.availability;

import com.durys.jakub.carfleet.cars.domain.Car;
import com.durys.jakub.carfleet.cars.domain.CarId;
import com.durys.jakub.carfleet.cars.domain.CarType;
import com.durys.jakub.carfleet.cars.domain.CarsRepository;
import com.durys.jakub.carfleet.cars.domain.basicinformation.FuelType;
import com.durys.jakub.carfleet.cars.domain.basicinformation.RegistrationNumber;
import com.durys.jakub.carfleet.cars.domain.basicinformation.Vin;
import com.durys.jakub.carfleet.cars.infrastructure.MockedCarsRepository;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

class DefaultCarAvailabilityServiceTest {

    private final CarsRepository carsRepository = new MockedCarsRepository();

    private final CarAvailabilityService carAvailabilityService
            = new DefaultCarAvailabilityService(null, carsRepository);


    @Test
    void shouldReturnCarAvailable() {

        CarId carId = new CarId(UUID.randomUUID());

        Car car = new Car(carId, CarType.Passenger,
                new RegistrationNumber("123"),
                new Vin("123"),
                FuelType.GASOLINE,
                Set.of());

        carsRepository.save(car);


        boolean available = carAvailabilityService.available(carId, LocalDateTime.now(), LocalDateTime.now().plusDays(3));

        assertTrue(available);
    }

}