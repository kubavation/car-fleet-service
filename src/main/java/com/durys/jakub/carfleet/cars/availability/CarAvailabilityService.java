package com.durys.jakub.carfleet.cars.availability;

import com.durys.jakub.carfleet.cars.domain.CarId;

import java.time.LocalDateTime;

public interface CarAvailabilityService {
    boolean available(CarId carId, LocalDateTime from, LocalDateTime to);
}
